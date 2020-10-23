package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.api.diary.DiaryEntriesResponse;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise.EditedExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise.ExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise.ExerciseEntryDetails;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.EditedFoodEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.FoodEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.FoodEntryDetails;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.note.NoteEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.note.NoteEntryDetails;
import pl.polsl.wachowski.nutritionassistant.api.exercise.Exercise;
import pl.polsl.wachowski.nutritionassistant.api.food.Food;
import pl.polsl.wachowski.nutritionassistant.api.food.NutrientDetails;
import pl.polsl.wachowski.nutritionassistant.db.entry.*;
import pl.polsl.wachowski.nutritionassistant.db.entry.views.*;
import pl.polsl.wachowski.nutritionassistant.exception.UserNotFoundException;
import pl.polsl.wachowski.nutritionassistant.exception.diary.EmptyDiaryException;
import pl.polsl.wachowski.nutritionassistant.repository.DiaryRepository;
import pl.polsl.wachowski.nutritionassistant.util.AmountConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DiaryService {

    private final AuthenticationService authenticationService;
    private final FoodService foodService;
    private final ExerciseService exerciseService;
    private final NoteService noteService;
    private final DiaryRepository diaryRepository;

    @Autowired
    public DiaryService(final AuthenticationService authenticationService,
                        final FoodService foodService,
                        final ExerciseService exerciseService,
                        final NoteService noteService,
                        final DiaryRepository diaryRepository) {
        this.authenticationService = authenticationService;
        this.foodService = foodService;
        this.exerciseService = exerciseService;
        this.noteService = noteService;
        this.diaryRepository = diaryRepository;
    }

    public DiaryEntriesResponse getDiaryEntries(final LocalDate date) {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        final UserDiaryEntrySimpleView diaryEntry = diaryRepository.findDiaryEntryByUserAndDate(userEmail, date);
        //TODO test this
        if (diaryEntry.getUserId() == null) {
            throw new UserNotFoundException();
        }
        if (diaryEntry.get() == null) {
            return DiaryEntriesResponse.EMPTY_INSTANCE;
        }

        final Set<FoodEntryDetails> foodEntries = diaryEntry.getFoodEntries()
                .stream()
                .map(this::toFoodEntryDetails)
                .collect(Collectors.toSet());
        final Set<ExerciseEntryDetails> exerciseEntries = diaryEntry.getExerciseEntries()
                .stream()
                .map(this::toExerciseEntryDetails)
                .collect(Collectors.toSet());
        final Set<NoteEntryDetails> noteEntries = diaryEntry.getNoteEntries()
                .stream()
                .map(DiaryService::toNoteEntryDetails)
                .collect(Collectors.toSet());
        return new DiaryEntriesResponse(foodEntries, exerciseEntries, noteEntries);
    }

    public void addFoodEntry(final LocalDate diaryDate, final FoodEntry foodEntry) {
        final UserDiaryEntryView diaryEntryView = getUserDiaryEntryView(diaryDate);
        final DiaryEntry diaryEntry = getOptionalDiaryEntry(diaryEntryView, diaryDate);
        final short entryPosition = (short) diaryEntryView.getEntriesCount();
        final FoodEntryEntity foodEntryEntity = new FoodEntryEntity(foodEntry.getId(),
                                                                    foodEntry.getNutritionDataProvider(),
                                                                    foodEntry.getMassUnit(),
                                                                    foodEntry.getAmount(),
                                                                    entryPosition,
                                                                    diaryEntry);
        diaryEntry.add(foodEntryEntity);
        diaryRepository.save(diaryEntry);
    }

    public void addExerciseEntry(final LocalDate diaryDate, final ExerciseEntry exerciseEntry) {
        final UserDiaryEntryView diaryEntryView = getUserDiaryEntryView(diaryDate);
        final DiaryEntry diaryEntry = getOptionalDiaryEntry(diaryEntryView, diaryDate);
        final short entryPosition = (short) diaryEntryView.getEntriesCount();
        final ExerciseEntryEntity exerciseEntryEntity = new ExerciseEntryEntity(exerciseEntry.getName(),
                                                                                exerciseEntry.getTimeUnit(),
                                                                                exerciseEntry.getDuration(),
                                                                                entryPosition,
                                                                                diaryEntry);
        diaryEntry.add(exerciseEntryEntity);
        diaryRepository.save(diaryEntry);
    }

    public void addNoteEntry(final LocalDate diaryDate, final NoteEntry noteEntry) {
        final UserDiaryEntryView diaryEntryView = getUserDiaryEntryView(diaryDate);
        final DiaryEntry diaryEntry = getOptionalDiaryEntry(diaryEntryView, diaryDate);
        final short entryPosition = (short) diaryEntryView.getEntriesCount();
        final NoteEntryEntity noteEntryEntity = new NoteEntryEntity(noteEntry.getContent(),
                                                                    entryPosition,
                                                                    diaryEntry);
        diaryEntry.add(noteEntryEntity);
        diaryRepository.save(diaryEntry);
    }

    public void editFoodEntry(final LocalDate diaryDate,
                              final short entryPosition,
                              final EditedFoodEntry editedFoodEntry) {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        final UserDiaryFoodEntriesView foodEntriesView = diaryRepository.findUserAndFoodEntriesByUserAndDate(userEmail,
                                                                                                             diaryDate);
        if (foodEntriesView.getUserId() == null) {
            throw new UserNotFoundException();
        }
        if (foodEntriesView.getDiaryId() == null) {
            throw new EmptyDiaryException("Diary for " + diaryDate + " is empty");
        }
        foodService.editFoodEntry(foodEntriesView.getFoodEntries(),
                                  entryPosition,
                                  editedFoodEntry);
    }

    public void editExerciseEntry(final LocalDate diaryDate,
                                  final short entryPosition,
                                  final EditedExerciseEntry editedExerciseEntry) {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        final UserDiaryExerciseEntriesView exerciseEntriesView = diaryRepository.findUserAndExerciseEntriesByUserAndDate(userEmail,
                                                                                                                         diaryDate);
        if (exerciseEntriesView.getUserId() == null) {
            throw new UserNotFoundException();
        }
        if (exerciseEntriesView.getDiaryId() == null) {
            throw new EmptyDiaryException("Diary for " + diaryDate + " is empty");
        }
        exerciseService.editExerciseEntry(exerciseEntriesView.getExerciseEntries(),
                                          entryPosition,
                                          editedExerciseEntry);
    }

    public void editNoteEntry(final LocalDate diaryDate,
                              final short entryPosition,
                              final NoteEntry editedNoteEntry) {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        final UserDiaryNoteEntriesView noteEntriesView = diaryRepository.findUserAndNoteEntriesByUserAndDate(userEmail,
                                                                                                             diaryDate);
        if (noteEntriesView.getUserId() == null) {
            throw new UserNotFoundException();
        }
        if (noteEntriesView.getDiaryId() == null) {
            throw new EmptyDiaryException("Diary for " + diaryDate + " is empty");
        }
        noteService.editNoteEntry(noteEntriesView.getNoteEntries(),
                                  entryPosition,
                                  editedNoteEntry);
    }

    public void deleteEntry(final LocalDate diaryDate, final short entryPosition) {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        final UserDiaryEntrySimpleView userDiaryView = diaryRepository.findDiaryEntryByUserAndDate(userEmail, diaryDate);
        if (userDiaryView.getUserId() == null) {
            throw new UserNotFoundException();
        }
        if (userDiaryView.get() == null) {
            throw new EmptyDiaryException("Diary for " + diaryDate + " is empty");
        }
        deleteByPosition(userDiaryView.getFoodEntries(), entryPosition);
        deleteByPosition(userDiaryView.getExerciseEntries(), entryPosition);
        deleteByPosition(userDiaryView.getNoteEntries(), entryPosition);
        Stream.of(userDiaryView.getFoodEntries(),
                  userDiaryView.getExerciseEntries(),
                  userDiaryView.getNoteEntries())
                .flatMap(List::stream)
                .filter(entry -> entry.getPosition() > entryPosition)
                .forEach(Sortable::moveUp);
        diaryRepository.save(userDiaryView.get());  //TODO test this
    }

    public void moveEntry(final LocalDate diaryDate,
                          final short oldPosition,
                          final short newPosition) {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        final UserDiaryEntrySimpleView userDiaryView = diaryRepository.findDiaryEntryByUserAndDate(userEmail, diaryDate);
        if (userDiaryView.getUserId() == null) {
            throw new UserNotFoundException();
        }
        if (userDiaryView.get() == null) {
            throw new EmptyDiaryException("Diary for " + diaryDate + " is empty");
        }
        final Map<Short, Short> positionChanges = getPositionChanges(oldPosition, newPosition);
        Stream.of(userDiaryView.getFoodEntries(),
                  userDiaryView.getExerciseEntries(),
                  userDiaryView.getNoteEntries())
                .flatMap(List::stream)
                .filter(entry -> positionChanges.containsKey(entry.getPosition()))
                .forEach(entry -> entry.setPosition(positionChanges.get(entry.getPosition())));
        diaryRepository.save(userDiaryView.get());
    }

    private UserDiaryEntryView getUserDiaryEntryView(final LocalDate diaryDate) {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        final UserDiaryEntryView diaryEntryView = diaryRepository.findUserAndDiaryEntryByUserAndDate(userEmail, diaryDate);
        if (diaryEntryView.getUser() == null) {
            throw new UserNotFoundException();
        }
        return diaryEntryView;
    }

    private static DiaryEntry getOptionalDiaryEntry(final UserDiaryEntryView userDiaryEntryView,
                                                    final LocalDate diaryDate) {
        return Optional.ofNullable(userDiaryEntryView.getDiaryEntry())
                .orElseGet(() -> new DiaryEntry(diaryDate, userDiaryEntryView.getUser()));
    }

    private FoodEntryDetails toFoodEntryDetails(final FoodEntryEntity foodEntry) {
        final Food food = foodService.getFood(foodEntry.getExternalId(), foodEntry.getProvider());
        final float factor = AmountConverter.getFoodAmountFactor(foodEntry.getAmount().floatValue(), foodEntry.getUnit());
        final Set<NutrientDetails> nutrients = food.getNutrients()
                .stream()
                .map(nutrient -> nutrient.multiply(factor))
                .collect(Collectors.toSet());
        return new FoodEntryDetails(food.getName(),
                                    food.getBrand(),
                                    foodEntry.getUnit(),
                                    foodEntry.getAmount(),
                                    nutrients,
                                    foodEntry.getPosition());
    }

    private ExerciseEntryDetails toExerciseEntryDetails(final ExerciseEntryEntity exerciseEntry) {
        final String exerciseName = exerciseEntry.getName();
        final Exercise exercise = exerciseService.searchExercises(exerciseName)
                .iterator()
                .next();
        final BigDecimal calories = AmountConverter.getExerciseCalories(exercise.getKcalPerMin(),
                                                                        exerciseEntry.getAmount(),
                                                                        exerciseEntry.getTimeUnit());
        return new ExerciseEntryDetails(exerciseName,
                                        exerciseEntry.getTimeUnit(),
                                        exerciseEntry.getAmount(),
                                        calories,
                                        exerciseEntry.getPosition());
    }

    private static NoteEntryDetails toNoteEntryDetails(final NoteEntryEntity noteEntry) {
        return new NoteEntryDetails(noteEntry.getContent(), noteEntry.getPosition());
    }

    private static Map<Short, Short> getPositionChanges(final short previousPosition, final short currentPosition) {
        final boolean movedUp = currentPosition < previousPosition;
        final int begin = movedUp ? currentPosition : previousPosition + 1;
        final int end = movedUp ? previousPosition - 1 : currentPosition;
        final int positionOffset = movedUp ? (short) 1 : (short) -1;

        final Map<Short, Short> positionChanges = new HashMap<>(end - begin + 1);
        positionChanges.put(previousPosition, currentPosition);
        for (int i = begin; i <= end; ++i)
            positionChanges.put((short) i, (short) (i + positionOffset));

        return positionChanges;
    }

    private static void deleteByPosition(final List<? extends Sortable> entries, final short position) {
        entries.stream()
                .filter(entry -> entry.getPosition().equals(position))
                .findFirst()
                .ifPresent(entries::remove);
    }

}
