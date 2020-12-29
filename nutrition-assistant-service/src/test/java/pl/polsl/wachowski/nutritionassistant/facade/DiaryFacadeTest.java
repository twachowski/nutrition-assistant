package pl.polsl.wachowski.nutritionassistant.facade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.EntryEditRequest;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.NewEntryRequest;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise.EditedExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise.ExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.EditedFoodEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.FoodEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.note.NoteEntry;
import pl.polsl.wachowski.nutritionassistant.exception.validation.ValidationException;
import pl.polsl.wachowski.nutritionassistant.service.DiaryService;
import pl.polsl.wachowski.nutritionassistant.samples.EntryEditRequestSamples;
import pl.polsl.wachowski.nutritionassistant.samples.NewEntryRequestSamples;
import pl.polsl.wachowski.nutritionassistant.util.ViolationUtils;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DiaryFacadeTest {

    private final DiaryFacade diaryFacade;

    DiaryFacadeTest() {
        final DiaryService diaryService = Mockito.mock(DiaryService.class);
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.diaryFacade = new DiaryFacade(diaryService, validator);
    }

    @Test
    @DisplayName("Should throw NPE when new entry request is null")
    void shouldThrowNpeWhenNewEntryRequestIsNull() {
        //given
        final NewEntryRequest request = null;
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    @DisplayName("Should throw NPE when new entry request has all null fields")
    void shouldThrowNpeWhenNewEntryRequestHasAllNullFields() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.withAllNullFields();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when food entry is null in new food entry request")
    void shouldThrowIllegalArgumentExceptionWhenFoodEntryIsNullInNewFoodEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.nullFoodRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when exercise entry is null in new exercise entry request")
    void shouldThrowIllegalArgumentExceptionWhenExerciseEntryIsNullInNewExerciseEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.nullExerciseRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when note entry is null in new note entry request")
    void shouldThrowIllegalArgumentExceptionWhenNoteEntryIsNullInNewNoteEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.nullNoteRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    @DisplayName("Should throw ValidationException when food entry has null id in new food entry request")
    void shouldThrowValidationExceptionWhenFoodEntryHasNullIdInNewFoodEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.nullIdFoodRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForFoodEntryId = exception.getViolations()
                .stream()
                .anyMatch(violation -> violation.getRootBeanClass().equals(FoodEntry.class)
                        && violation.getPropertyPath().toString().equals("id"));
        assertTrue(hasViolationForFoodEntryId);
    }

    @Test
    @DisplayName("Should throw ValidationException when food entry has empty id in new food entry request")
    void shouldThrowValidationExceptionWhenFoodEntryHasEmptyIdInNewFoodEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.emptyIdFoodRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForFoodEntryId = exception.getViolations()
                .stream()
                .anyMatch(violation -> violation.getRootBeanClass().equals(FoodEntry.class)
                        && violation.getPropertyPath().toString().equals("id"));
        assertTrue(hasViolationForFoodEntryId);
    }

    @Test
    @DisplayName("Should throw ValidationException when food entry has blank id in new food entry request")
    void shouldThrowValidationExceptionWhenFoodEntryHasBlankIdInNewFoodEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.blankIdFoodRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForFoodEntryId = ViolationUtils.hasViolation(exception,
                                                                               FoodEntry.class,
                                                                               "id");
        assertTrue(hasViolationForFoodEntryId);
    }

    @Test
    @DisplayName("Should throw ValidationException when food entry has null provider in new food entry request")
    void shouldThrowValidationExceptionWhenFoodEntryHasNullProviderInNewFoodEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.nullProviderFoodRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForFoodEntryProvider = ViolationUtils.hasViolation(exception,
                                                                                     FoodEntry.class,
                                                                                     "nutritionDataProvider");
        assertTrue(hasViolationForFoodEntryProvider);
    }

    @Test
    @DisplayName("Should throw ValidationException when food entry has null mass unit in new food entry request")
    void shouldThrowValidationExceptionWhenFoodEntryHasNullMassUnitInNewFoodEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.nullMassUnitFoodRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForFoodEntryMassUnit = ViolationUtils.hasViolation(exception,
                                                                                     FoodEntry.class,
                                                                                     "massUnit");
        assertTrue(hasViolationForFoodEntryMassUnit);
    }

    @Test
    @DisplayName("Should throw ValidationException when food entry has null amount in new food entry request")
    void shouldThrowValidationExceptionWhenFoodEntryHasNullAmountInNewFoodEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.nullAmountFoodRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForFoodEntryMassUnit = ViolationUtils.hasViolation(exception,
                                                                                     FoodEntry.class,
                                                                                     "amount");
        assertTrue(hasViolationForFoodEntryMassUnit);
    }

    @Test
    @DisplayName("Should throw ValidationException when food entry has negative amount in new food entry request")
    void shouldThrowValidationExceptionWhenFoodEntryHasNegativeAmountInNewFoodEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.negativeAmountFoodRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForFoodEntryAmount = ViolationUtils.hasViolation(exception,
                                                                                   FoodEntry.class,
                                                                                   "amount");
        assertTrue(hasViolationForFoodEntryAmount);
    }

    @Test
    @DisplayName("Should throw ValidationException when food entry has zero amount in new food entry request")
    void shouldThrowValidationExceptionWhenFoodEntryHasZeroAmountInNewFoodEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.zeroAmountFoodRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForFoodEntryAmount = ViolationUtils.hasViolation(exception,
                                                                                   FoodEntry.class,
                                                                                   "amount");
        assertTrue(hasViolationForFoodEntryAmount);
    }

    @Test
    @DisplayName("Should add food entry")
    void shouldAddFoodEntry() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.validFoodRequest();
        final LocalDate diaryDate = LocalDate.now();

        //when
        diaryFacade.addEntry(diaryDate, request);

        //then
        //no exception is thrown
    }

    @Test
    @DisplayName("Should throw ValidationException when exercise entry has null name in new exercise entry request")
    void shouldThrowValidationExceptionWhenExerciseEntryHasNullNameInNewExerciseEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.nullNameExerciseRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForExerciseEntryName = ViolationUtils.hasViolation(exception,
                                                                                     ExerciseEntry.class,
                                                                                     "name");
        assertTrue(hasViolationForExerciseEntryName);
    }

    @Test
    @DisplayName("Should throw ValidationException when exercise entry has empty name in new exercise entry request")
    void shouldThrowValidationExceptionWhenExerciseEntryHasEmptyNameInNewExerciseEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.emptyNameExerciseRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForExerciseEntryName = ViolationUtils.hasViolation(exception,
                                                                                     ExerciseEntry.class,
                                                                                     "name");
        assertTrue(hasViolationForExerciseEntryName);
    }

    @Test
    @DisplayName("Should throw ValidationException when exercise entry has blank name in new exercise entry request")
    void shouldThrowValidationExceptionWhenExerciseEntryHasBlankNameInNewExerciseEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.blankNameExerciseRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForExerciseEntryName = ViolationUtils.hasViolation(exception,
                                                                                     ExerciseEntry.class,
                                                                                     "name");
        assertTrue(hasViolationForExerciseEntryName);
    }

    @Test
    @DisplayName("Should throw ValidationException when exercise entry has null time unit in new exercise entry request")
    void shouldThrowValidationExceptionWhenExerciseEntryHasNullTimeUnitInNewExerciseEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.nullTimeUnitExerciseRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForExerciseEntryTimeUnit = ViolationUtils.hasViolation(exception,
                                                                                         ExerciseEntry.class,
                                                                                         "timeUnit");
        assertTrue(hasViolationForExerciseEntryTimeUnit);
    }

    @Test
    @DisplayName("Should throw ValidationException when exercise entry has null duration in new exercise entry request")
    void shouldThrowValidationExceptionWhenExerciseEntryHasNullDurationInNewExerciseEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.nullDurationExerciseRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForExerciseEntryDuration = ViolationUtils.hasViolation(exception,
                                                                                         ExerciseEntry.class,
                                                                                         "duration");
        assertTrue(hasViolationForExerciseEntryDuration);
    }

    @Test
    @DisplayName("Should throw ValidationException when exercise entry has negative duration in new exercise entry request")
    void shouldThrowValidationExceptionWhenExerciseEntryHasNegativeDurationInNewExerciseEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.negativeDurationExerciseRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForExerciseEntryDuration = ViolationUtils.hasViolation(exception,
                                                                                         ExerciseEntry.class,
                                                                                         "duration");
        assertTrue(hasViolationForExerciseEntryDuration);
    }

    @Test
    @DisplayName("Should throw ValidationException when exercise entry has zero duration in new exercise entry request")
    void shouldThrowValidationExceptionWhenExerciseEntryHasZeroDurationInNewExerciseEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.zeroDurationExerciseRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForExerciseEntryDuration = ViolationUtils.hasViolation(exception,
                                                                                         ExerciseEntry.class,
                                                                                         "duration");
        assertTrue(hasViolationForExerciseEntryDuration);
    }

    @Test
    @DisplayName("Should add exercise entry")
    void shouldAddExerciseEntry() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.validExerciseRequest();
        final LocalDate diaryDate = LocalDate.now();

        //when
        diaryFacade.addEntry(diaryDate, request);

        //then
        //no exception is thrown
    }

    @Test
    @DisplayName("Should throw ValidationException when note entry has null content in new note entry request")
    void shouldThrowValidationExceptionWhenNoteEntryHasNullContentInNewNoteEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.nullContentNoteRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForNoteEntryContent = ViolationUtils.hasViolation(exception,
                                                                                    NoteEntry.class,
                                                                                    "content");
        assertTrue(hasViolationForNoteEntryContent);
    }

    @Test
    @DisplayName("Should throw ValidationException when note entry has empty content in new note entry request")
    void shouldThrowValidationExceptionWhenNoteEntryHasEmptyContentInNewNoteEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.emptyContentNoteRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForNoteEntryContent = ViolationUtils.hasViolation(exception,
                                                                                    NoteEntry.class,
                                                                                    "content");
        assertTrue(hasViolationForNoteEntryContent);
    }

    @Test
    @DisplayName("Should throw ValidationException when note entry has blank content in new note entry request")
    void shouldThrowValidationExceptionWhenNoteEntryHasBlankContentInNewNoteEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.blankContentNoteRequest();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForNoteEntryContent = ViolationUtils.hasViolation(exception,
                                                                                    NoteEntry.class,
                                                                                    "content");
        assertTrue(hasViolationForNoteEntryContent);
    }

    @Test
    @DisplayName("Should throw ValidationException when note entry has content of length higher than 255 in new note entry request")
    void shouldThrowValidationExceptionWhenNoteEntryHasContentOfLengthHigherThan255InNewNoteEntryRequest() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.noteRequestWith256ContentLength();
        final Executable executable = () -> diaryFacade.addEntry(LocalDate.now(), request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForNoteEntryContent = ViolationUtils.hasViolation(exception,
                                                                                    NoteEntry.class,
                                                                                    "content");
        assertTrue(hasViolationForNoteEntryContent);
    }

    @Test
    @DisplayName("Should add note entry")
    void shouldAddNoteEntry() {
        //given
        final NewEntryRequest request = NewEntryRequestSamples.validNoteRequest();
        final LocalDate diaryDate = LocalDate.now();

        //when
        diaryFacade.addEntry(diaryDate, request);

        //then
        //no exception is thrown
    }

    @Test
    @DisplayName("Should throw NPE when entry edit request is null")
    void shouldThrowNpeWhenEntryEditRequestIsNull() {
        //given
        final EntryEditRequest request = null;
        final Executable executable = () -> diaryFacade.editEntry(LocalDate.now(), (short) 0, request);

        //when

        //then
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    @DisplayName("Should throw NPE when entry edit request has all null fields")
    void shouldThrowNpeWhenEntryEditRequestHasAllNullFields() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.withAllNullFields();
        final Executable executable = () -> diaryFacade.editEntry(LocalDate.now(), (short) 0, request);

        //when

        //then
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when food entry is null in food entry edit request")
    void shouldThrowIllegalArgumentExceptionWhenFoodEntryIsNullInFoodEntryEditRequest() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.nullFoodRequest();
        final Executable executable = () -> diaryFacade.editEntry(LocalDate.now(), (short) 0, request);

        //when

        //then
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when exercise entry is null in exercise entry edit request")
    void shouldThrowIllegalArgumentExceptionWhenExerciseEntryIsNullInExerciseEntryEditRequest() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.nullExerciseRequest();
        final Executable executable = () -> diaryFacade.editEntry(LocalDate.now(), (short) 0, request);

        //when

        //then
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when note entry is null in note entry edit request")
    void shouldThrowIllegalArgumentExceptionWhenNoteEntryIsNullInNoteEntryEditRequest() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.nullNoteRequest();
        final Executable executable = () -> diaryFacade.editEntry(LocalDate.now(), (short) 0, request);

        //when

        //then
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    @DisplayName("Should throw ValidationException when food entry has null mass unit in food entry edit request")
    void shouldThrowValidationExceptionWhenFoodEntryHasNullMassUnitInFoodEntryEditRequest() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.nullMassUnitFoodRequest();
        final Executable executable = () -> diaryFacade.editEntry(LocalDate.now(), (short) 0, request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForFoodEntryMassUnit = ViolationUtils.hasViolation(exception,
                                                                                     EditedFoodEntry.class,
                                                                                     "massUnit");
        assertTrue(hasViolationForFoodEntryMassUnit);
    }

    @Test
    @DisplayName("Should throw ValidationException when food entry has null amount in food entry edit request")
    void shouldThrowValidationExceptionWhenFoodEntryHasNullAmountInFoodEntryEditRequest() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.nullAmountFoodRequest();
        final Executable executable = () -> diaryFacade.editEntry(LocalDate.now(), (short) 0, request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForFoodEntryAmount = ViolationUtils.hasViolation(exception,
                                                                                   EditedFoodEntry.class,
                                                                                   "amount");
        assertTrue(hasViolationForFoodEntryAmount);
    }

    @Test
    @DisplayName("Should throw ValidationException when food entry has negative amount in food entry edit request")
    void shouldThrowValidationExceptionWhenFoodEntryHasNegativeAmountInFoodEntryEditRequest() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.negativeAmountFoodRequest();
        final Executable executable = () -> diaryFacade.editEntry(LocalDate.now(), (short) 0, request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForFoodEntryAmount = ViolationUtils.hasViolation(exception,
                                                                                   EditedFoodEntry.class,
                                                                                   "amount");
        assertTrue(hasViolationForFoodEntryAmount);
    }

    @Test
    @DisplayName("Should throw ValidationException when food entry has zero amount in food entry edit request")
    void shouldThrowValidationExceptionWhenFoodEntryHasZeroAmountInFoodEntryEditRequest() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.zeroAmountFoodRequest();
        final Executable executable = () -> diaryFacade.editEntry(LocalDate.now(), (short) 0, request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForFoodEntryAmount = ViolationUtils.hasViolation(exception,
                                                                                   EditedFoodEntry.class,
                                                                                   "amount");
        assertTrue(hasViolationForFoodEntryAmount);
    }

    @Test
    @DisplayName("Should edit food entry")
    void shouldEditFoodEntry() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.validFoodRequest();
        final LocalDate diaryDate = LocalDate.now();
        final short entryPosition = 0;

        //when
        diaryFacade.editEntry(diaryDate, entryPosition, request);

        //then
        //no exception is thrown
    }

    @Test
    @DisplayName("Should throw ValidationException when exercise entry has null time unit in exercise entry edit request")
    void shouldThrowValidationExceptionWhenExerciseEntryHasNullTimeUnitInExerciseEntryEditRequest() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.nullTimeUnitExerciseRequest();
        final Executable executable = () -> diaryFacade.editEntry(LocalDate.now(), (short) 0, request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForExerciseEntryTimeUnit = ViolationUtils.hasViolation(exception,
                                                                                         EditedExerciseEntry.class,
                                                                                         "timeUnit");
        assertTrue(hasViolationForExerciseEntryTimeUnit);
    }

    @Test
    @DisplayName("Should throw ValidationException when exercise entry has null duration in exercise entry edit request")
    void shouldThrowValidationExceptionWhenExerciseEntryHasNullDurationInExerciseEntryEditRequest() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.nullDurationExerciseRequest();
        final Executable executable = () -> diaryFacade.editEntry(LocalDate.now(), (short) 0, request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForExerciseEntryAmount = ViolationUtils.hasViolation(exception,
                                                                                       EditedExerciseEntry.class,
                                                                                       "duration");
        assertTrue(hasViolationForExerciseEntryAmount);
    }

    @Test
    @DisplayName("Should throw ValidationException when exercise entry has negative duration in exercise entry edit request")
    void shouldThrowValidationExceptionWhenExerciseEntryHasNegativeDurationInExerciseEntryEditRequest() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.negativeDurationExerciseRequest();
        final Executable executable = () -> diaryFacade.editEntry(LocalDate.now(), (short) 0, request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForExerciseEntryAmount = ViolationUtils.hasViolation(exception,
                                                                                       EditedExerciseEntry.class,
                                                                                       "duration");
        assertTrue(hasViolationForExerciseEntryAmount);
    }

    @Test
    @DisplayName("Should throw ValidationException when exercise entry has zero duration in exercise entry edit request")
    void shouldThrowValidationExceptionWhenExerciseEntryHasZeroDurationInExerciseEntryEditRequest() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.zeroDurationExerciseRequest();
        final Executable executable = () -> diaryFacade.editEntry(LocalDate.now(), (short) 0, request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForExerciseEntryAmount = ViolationUtils.hasViolation(exception,
                                                                                       EditedExerciseEntry.class,
                                                                                       "duration");
        assertTrue(hasViolationForExerciseEntryAmount);
    }

    @Test
    @DisplayName("Should edit exercise entry")
    void shouldEditExerciseEntry() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.validExerciseRequest();
        final LocalDate diaryDate = LocalDate.now();
        final short entryPosition = 0;

        //when
        diaryFacade.editEntry(diaryDate, entryPosition, request);

        //then
        //no exception is thrown
    }

    @Test
    @DisplayName("Should throw ValidationException when note entry has null content in note entry edit request")
    void shouldThrowValidationExceptionWhenNoteEntryHasNullContentInNoteEntryEditRequest() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.nullContentNoteRequest();
        final Executable executable = () -> diaryFacade.editEntry(LocalDate.now(), (short) 0, request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForNoteEntryContent = ViolationUtils.hasViolation(exception,
                                                                                    NoteEntry.class,
                                                                                    "content");
        assertTrue(hasViolationForNoteEntryContent);
    }

    @Test
    @DisplayName("Should throw ValidationException when note entry has empty content in note entry edit request")
    void shouldThrowValidationExceptionWhenNoteEntryHasEmptyContentInNoteEntryEditRequest() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.emptyContentNoteRequest();
        final Executable executable = () -> diaryFacade.editEntry(LocalDate.now(), (short) 0, request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForNoteEntryContent = ViolationUtils.hasViolation(exception,
                                                                                    NoteEntry.class,
                                                                                    "content");
        assertTrue(hasViolationForNoteEntryContent);
    }

    @Test
    @DisplayName("Should throw ValidationException when note entry has blank content in note entry edit request")
    void shouldThrowValidationExceptionWhenNoteEntryHasBlankContentInNoteEntryEditRequest() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.blankContentNoteRequest();
        final Executable executable = () -> diaryFacade.editEntry(LocalDate.now(), (short) 0, request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForNoteEntryContent = ViolationUtils.hasViolation(exception,
                                                                                    NoteEntry.class,
                                                                                    "content");
        assertTrue(hasViolationForNoteEntryContent);
    }

    @Test
    @DisplayName("Should throw ValidationException when note entry has content of length higher than 255 in note entry edit request")
    void shouldThrowValidationExceptionWhenNoteEntryHasContentOfLengthHigherThan255InNoteEntryEditRequest() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.noteRequestWith256ContentLength();
        final Executable executable = () -> diaryFacade.editEntry(LocalDate.now(), (short) 0, request);

        //when

        //then
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        final boolean hasViolationForNoteEntryContent = ViolationUtils.hasViolation(exception,
                                                                                    NoteEntry.class,
                                                                                    "content");
        assertTrue(hasViolationForNoteEntryContent);
    }

    @Test
    @DisplayName("Should edit note entry")
    void shouldEditNoteEntry() {
        //given
        final EntryEditRequest request = EntryEditRequestSamples.validNoteRequest();
        final LocalDate diaryDate = LocalDate.now();
        final short entryPosition = 0;

        //when
        diaryFacade.editEntry(diaryDate, entryPosition, request);

        //then
        //no exception is thrown
    }

}