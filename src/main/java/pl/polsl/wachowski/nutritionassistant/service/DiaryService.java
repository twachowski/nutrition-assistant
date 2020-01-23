package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.db.entry.DiaryEntry;
import pl.polsl.wachowski.nutritionassistant.db.entry.ExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.db.entry.FoodEntry;
import pl.polsl.wachowski.nutritionassistant.db.entry.NoteEntry;
import pl.polsl.wachowski.nutritionassistant.db.user.User;
import pl.polsl.wachowski.nutritionassistant.dto.details.FoodDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.details.NutrientDetailDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.DiaryEntriesResponseDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.exercise.ExerciseEntryDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.exercise.ExerciseEntryDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.exercise.NewExerciseEntryRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.food.FoodEntryDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.food.FoodEntryDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.food.NewFoodEntryRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.note.NewNoteEntryRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.note.NoteEntryDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.ExerciseDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.ExerciseSearchRequestDTO;
import pl.polsl.wachowski.nutritionassistant.repository.DiaryRepository;
import pl.polsl.wachowski.nutritionassistant.repository.UserRepository;
import pl.polsl.wachowski.nutritionassistant.util.AmountConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiaryService {

    private final FoodService foodService;

    private final ExerciseService exerciseService;

    private final UserRepository userRepository;

    private final DiaryRepository diaryRepository;

    @Autowired
    public DiaryService(final UserRepository userRepository,
                        final DiaryRepository diaryRepository,
                        final FoodService foodService,
                        final ExerciseService exerciseService) {
        this.userRepository = userRepository;
        this.diaryRepository = diaryRepository;
        this.foodService = foodService;
        this.exerciseService = exerciseService;
    }

    public DiaryEntriesResponseDTO getDiaryEntries(final String userEmail, final LocalDate diaryDate) {
        final User user = userRepository.findUserByEmail(userEmail);
        final DiaryEntry diaryEntry = diaryRepository.findDiaryEntryByUserAndDateFetchFoodEntries(user, diaryDate);
        final List<FoodEntryDetailsDTO> foodEntries =
                diaryEntry.getFoodEntries()
                        .stream()
                        .map(this::mapFoodEntry)
                        .collect(Collectors.toList());
        final List<ExerciseEntryDetailsDTO> exerciseEntries =
                diaryEntry.getExerciseEntries()
                        .stream()
                        .map(exercise -> mapExerciseEntry(exercise, userEmail))
                        .collect(Collectors.toList());
        final List<NoteEntryDTO> noteEntries =
                diaryEntry.getNoteEntries()
                        .stream()
                        .map(this::mapNoteEntry)
                        .collect(Collectors.toList());
        return new DiaryEntriesResponseDTO(foodEntries, exerciseEntries, noteEntries);
    }

    public void addFoodEntry(final NewFoodEntryRequestDTO request) {
        final DiaryEntry diaryEntry = findOrCreateDiaryEntry(request.getUser(), request.getDiaryDate());
        final FoodEntry foodEntry = createFoodEntry(request.getFoodEntry(), diaryEntry);
        diaryEntry.getFoodEntries().add(foodEntry);
        diaryRepository.save(diaryEntry);
    }

    public void addExerciseEntry(final NewExerciseEntryRequestDTO request) {
        final DiaryEntry diaryEntry = findOrCreateDiaryEntry(request.getUser(), request.getDiaryDate());
        final ExerciseEntry exerciseEntry = createExerciseEntry(request.getExerciseEntry(), diaryEntry);
        diaryEntry.getExerciseEntries().add(exerciseEntry);
        diaryRepository.save(diaryEntry);
    }

    public void addNoteEntry(final NewNoteEntryRequestDTO request) {
        final DiaryEntry diaryEntry = findOrCreateDiaryEntry(request.getUser(), request.getDiaryDate());
        final NoteEntry noteEntry = createNoteEntry(request.getNoteEntry(), diaryEntry);
        diaryEntry.getNoteEntries().add(noteEntry);
        diaryRepository.save(diaryEntry);
    }

    private DiaryEntry findOrCreateDiaryEntry(final String userEmail, final LocalDate date) {
        final User user = userRepository.findUserByEmail(userEmail);
        final DiaryEntry entry = diaryRepository.findDiaryEntryByUserAndDate(user, date);
        return entry != null ? entry : new DiaryEntry(date, user);
    }

    private FoodEntry createFoodEntry(final FoodEntryDTO entry, final DiaryEntry diaryEntry) {
        return new FoodEntry(
                entry.getExternalId(),
                entry.getProvider(),
                entry.getUnit(),
                entry.getAmount(),
                entry.getPosition(),
                diaryEntry);
    }

    private ExerciseEntry createExerciseEntry(final ExerciseEntryDTO entry, final DiaryEntry diaryEntry) {
        return new ExerciseEntry(
                entry.getName(),
                entry.getUnit(),
                entry.getAmount(),
                entry.getPosition(),
                diaryEntry);
    }

    private NoteEntry createNoteEntry(final NoteEntryDTO entry, final DiaryEntry diaryEntry) {
        return new NoteEntry(entry.getContent(), entry.getPosition(), diaryEntry);
    }

    private FoodEntryDetailsDTO mapFoodEntry(final FoodEntry foodEntry) {
        final FoodDetailsDTO foodDetails = foodService.getDetails(foodEntry.getExternalId(), foodEntry.getProvider());
        final List<NutrientDetailDTO> nutrientDetails = foodDetails.getNutrientDetails();
        final float factor = AmountConverter.getFoodAmountFactor(foodEntry.getAmount().floatValue(), foodEntry.getUnit());
        nutrientDetails.forEach(nd -> nd.multiply(factor));

        return new FoodEntryDetailsDTO(
                foodDetails.getName(),
                foodDetails.getBrandName(),
                foodEntry.getAmount(),
                foodEntry.getUnit(),
                foodEntry.getPosition(),
                nutrientDetails);
    }

    private ExerciseEntryDetailsDTO mapExerciseEntry(final ExerciseEntry exerciseEntry, final String user) {
        final ExerciseSearchRequestDTO request = new ExerciseSearchRequestDTO(user, exerciseEntry.getName());
        final ExerciseDetailsDTO exercise = exerciseService.search(request).getExercises().get(0);
        final float coeff = AmountConverter.getExerciseDurationCoeff(
                exercise.getDurationMin(),
                exerciseEntry.getAmount().floatValue(),
                exerciseEntry.getTimeUnit());
        final float calories = exercise.getCalories() * coeff;

        return new ExerciseEntryDetailsDTO(
                exerciseEntry.getName(),
                exerciseEntry.getAmount(),
                exerciseEntry.getTimeUnit(),
                BigDecimal.valueOf(calories),
                exerciseEntry.getPosition());
    }

    private NoteEntryDTO mapNoteEntry(final NoteEntry noteEntry) {
        return new NoteEntryDTO(noteEntry.getContent(), noteEntry.getPosition());
    }

}
