package pl.polsl.wachowski.nutritionassistant.service;

import lombok.extern.slf4j.Slf4j;
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
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.*;
import pl.polsl.wachowski.nutritionassistant.domain.repository.DiaryRepository;
import pl.polsl.wachowski.nutritionassistant.exception.entry.EntryNotFoundException;
import pl.polsl.wachowski.nutritionassistant.facade.EntryFacade;
import pl.polsl.wachowski.nutritionassistant.util.AmountConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class DiaryService {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final EntryFacade entryFacade;
    private final DiaryRepository diaryRepository;

    @Autowired
    public DiaryService(final AuthenticationService authenticationService,
                        final UserService userService,
                        final EntryFacade entryFacade,
                        final DiaryRepository diaryRepository) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.entryFacade = entryFacade;
        this.diaryRepository = diaryRepository;
    }

    public DiaryEntriesResponse getDiaryEntries(final LocalDate diaryDate) {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        final DiaryEntryEntity diaryEntry = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate);
        if (diaryEntry == null) {
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
        final DiaryEntryEntity diaryEntry = getOptionalUserDiaryEntry(diaryDate);
        final short entryPosition = (short) diaryEntry.getSize();
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
        final DiaryEntryEntity diaryEntry = getOptionalUserDiaryEntry(diaryDate);
        final short entryPosition = (short) diaryEntry.getSize();
        final ExerciseEntryEntity exerciseEntryEntity = new ExerciseEntryEntity(exerciseEntry.getName(),
                                                                                exerciseEntry.getTimeUnit(),
                                                                                exerciseEntry.getDuration(),
                                                                                entryPosition,
                                                                                diaryEntry);
        diaryEntry.add(exerciseEntryEntity);
        diaryRepository.save(diaryEntry);
    }

    public void addNoteEntry(final LocalDate diaryDate, final NoteEntry noteEntry) {
        final DiaryEntryEntity diaryEntry = getOptionalUserDiaryEntry(diaryDate);
        final short entryPosition = (short) diaryEntry.getSize();
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
        final DiaryEntryEntity diaryEntry = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate);
        if (diaryEntry == null) {
            log.error("Cannot edit food entry at {} position - diary entry could not be found or is empty for date: {}",
                      entryPosition,
                      diaryDate);
            throw EntryNotFoundException.emptyDiary(diaryDate);
        }
        entryFacade.editFoodEntry(diaryEntry.getFoodEntries(),
                                  entryPosition,
                                  editedFoodEntry);
    }

    public void editExerciseEntry(final LocalDate diaryDate,
                                  final short entryPosition,
                                  final EditedExerciseEntry editedExerciseEntry) {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        final DiaryEntryEntity diaryEntry = diaryRepository.findDiaryEntryFetchExerciseEntries(userEmail, diaryDate);
        if (diaryEntry == null) {
            log.error("Cannot edit exercise entry at {} position - diary entry could not be found or is empty for date: {}",
                      entryPosition,
                      diaryDate);
            throw EntryNotFoundException.emptyDiary(diaryDate);
        }
        entryFacade.editExerciseEntry(diaryEntry.getExerciseEntries(),
                                      entryPosition,
                                      editedExerciseEntry);
    }

    public void editNoteEntry(final LocalDate diaryDate,
                              final short entryPosition,
                              final NoteEntry editedNoteEntry) {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        final DiaryEntryEntity diaryEntry = diaryRepository.findDiaryEntryFetchNoteEntries(userEmail, diaryDate);
        if (diaryEntry == null) {
            log.error("Cannot edit note entry at {} position - diary entry could not be found or is empty for date: {}",
                      entryPosition,
                      diaryDate);
            throw EntryNotFoundException.emptyDiary(diaryDate);
        }
        entryFacade.editNoteEntry(diaryEntry.getNoteEntries(),
                                  entryPosition,
                                  editedNoteEntry);
    }

    public void deleteEntry(final LocalDate diaryDate, final short entryPosition) {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        final DiaryEntryEntity diaryEntry = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate);
        if (diaryEntry == null) {
            log.error("Cannot delete entry at {} position - diary entry could not be found or is empty for date: {}",
                      entryPosition,
                      diaryDate);
            throw EntryNotFoundException.emptyDiary(diaryDate);
        }
        final int removedEntriesCount = Stream.of(deleteByPosition(diaryEntry.getFoodEntries(), entryPosition),
                                                  deleteByPosition(diaryEntry.getExerciseEntries(), entryPosition),
                                                  deleteByPosition(diaryEntry.getNoteEntries(), entryPosition))
                .reduce(0, Integer::sum);
        if (removedEntriesCount < 1) {
            log.error("No entry has been deleted - could not find entry at position {}", entryPosition);
            throw new EntryNotFoundException("Could not find entry at position " + entryPosition);
        }
        diaryEntry.entries()
                .filter(entry -> entry.getPosition() > entryPosition)
                .forEach(Sortable::moveUp);
        diaryRepository.save(diaryEntry);
    }

    public void moveEntry(final LocalDate diaryDate,
                          final short oldPosition,
                          final short newPosition) {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        final DiaryEntryEntity diaryEntry = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate);
        if (diaryEntry == null) {
            log.error("Cannot move entry from {} to {} - diary entry could not be found or is empty for date: {}",
                      oldPosition,
                      newPosition,
                      diaryDate);
            throw EntryNotFoundException.emptyDiary(diaryDate);
        }
        if (!diaryEntry.hasEntryAtPosition(oldPosition)) {
            log.error("Diary entry has no entry at old position {}", oldPosition);
            throw new EntryNotFoundException("Could not find entry at old position: " + oldPosition);
        }
        if (!diaryEntry.hasEntryAtPosition(newPosition)) {
            log.error("Diary entry has no entry at new position {}", newPosition);
            throw new EntryNotFoundException("Could not find entry at new position: " + newPosition);
        }
        final Map<Short, Short> positionChanges = getPositionChanges(oldPosition, newPosition);
        diaryEntry.entries()
                .filter(entry -> positionChanges.containsKey(entry.getPosition()))
                .forEach(entry -> entry.setPosition(positionChanges.get(entry.getPosition())));
        diaryRepository.save(diaryEntry);
    }

    private DiaryEntryEntity getOptionalUserDiaryEntry(final LocalDate diaryDate) {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        return Optional.ofNullable(diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate))
                .orElseGet(() -> new DiaryEntryEntity(diaryDate, userService.getUserByEmail(userEmail)));
    }

    private FoodEntryDetails toFoodEntryDetails(final FoodEntryEntity foodEntry) {
        final Food food = entryFacade.getFood(foodEntry.getExternalId(), foodEntry.getProvider());
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
        final Exercise exercise = entryFacade.searchExercises(exerciseName)
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
        if (previousPosition == currentPosition) {
            return Collections.emptyMap();
        }
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

    private static int deleteByPosition(final List<? extends Sortable> entries, final short position) {
        final boolean removed = entries.stream()
                .filter(entry -> entry.getPosition().equals(position))
                .findFirst()
                .map(entries::remove)
                .orElse(false);
        return removed ? 1 : 0;
    }

}
