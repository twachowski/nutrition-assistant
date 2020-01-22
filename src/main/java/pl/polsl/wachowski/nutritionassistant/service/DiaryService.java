package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.db.entry.DiaryEntry;
import pl.polsl.wachowski.nutritionassistant.db.entry.ExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.db.entry.FoodEntry;
import pl.polsl.wachowski.nutritionassistant.db.entry.NoteEntry;
import pl.polsl.wachowski.nutritionassistant.db.user.User;
import pl.polsl.wachowski.nutritionassistant.dto.diary.exercise.ExerciseEntryDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.exercise.NewExerciseEntryRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.food.FoodEntryDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.food.NewFoodEntryRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.note.NewNoteEntryRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.note.NoteEntryDTO;
import pl.polsl.wachowski.nutritionassistant.repository.DiaryRepository;
import pl.polsl.wachowski.nutritionassistant.repository.UserRepository;

import java.time.LocalDate;

@Service
public class DiaryService {

    private final UserRepository userRepository;

    private final DiaryRepository diaryRepository;

    @Autowired
    public DiaryService(final UserRepository userRepository, final DiaryRepository diaryRepository) {
        this.userRepository = userRepository;
        this.diaryRepository = diaryRepository;
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

}
