package pl.polsl.wachowski.nutritionassistant.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.api.diary.DiaryEntriesResponse;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.EntryEditRequest;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.EntryMoveRequest;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.EntryType;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.NewEntryRequest;
import pl.polsl.wachowski.nutritionassistant.exception.validation.ValidationException;
import pl.polsl.wachowski.nutritionassistant.service.DiaryService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

@Component
public class DiaryFacade {

    private final DiaryService diaryService;
    private final Validator validator;

    @Autowired
    public DiaryFacade(final DiaryService diaryService, final Validator validator) {
        this.diaryService = diaryService;
        this.validator = validator;
    }

    public DiaryEntriesResponse getEntries(final LocalDate diaryDate) {
        return diaryService.getDiaryEntries(diaryDate);
    }

    public void addEntry(final LocalDate diaryDate, final NewEntryRequest newEntryRequest) {
        if (newEntryRequest.getEntryType().equals(EntryType.FOOD)) {
            validate(newEntryRequest.getFoodEntry());
            diaryService.addFoodEntry(diaryDate, newEntryRequest.getFoodEntry());
        } else if (newEntryRequest.getEntryType().equals(EntryType.EXERCISE)) {
            validate(newEntryRequest.getExerciseEntry());
            diaryService.addExerciseEntry(diaryDate, newEntryRequest.getExerciseEntry());
        } else {
            validate(newEntryRequest.getNoteEntry());
            diaryService.addNoteEntry(diaryDate, newEntryRequest.getNoteEntry());
        }
    }

    public void editEntry(final LocalDate diaryDate,
                          final short entryPosition,
                          final EntryEditRequest entryEditRequest) {
        if (entryEditRequest.getEntryType().equals(EntryType.FOOD)) {
            validate(entryEditRequest.getEditedFoodEntry());
            diaryService.editFoodEntry(diaryDate,
                                       entryPosition,
                                       entryEditRequest.getEditedFoodEntry());
        } else if (entryEditRequest.getEntryType().equals(EntryType.EXERCISE)) {
            validate(entryEditRequest.getEditedExerciseEntry());
            diaryService.editExerciseEntry(diaryDate,
                                           entryPosition,
                                           entryEditRequest.getEditedExerciseEntry());
        } else {
            validate(entryEditRequest.getEditedNoteEntry());
            diaryService.editNoteEntry(diaryDate,
                                       entryPosition,
                                       entryEditRequest.getEditedNoteEntry());
        }
    }

    public void deleteEntry(final LocalDate diaryDate, final short entryPosition) {
        diaryService.deleteEntry(diaryDate, entryPosition);
    }

    public void moveEntry(final LocalDate diaryDate, final EntryMoveRequest request) {
        if (!request.isPositionUchanged()) {
            diaryService.moveEntry(diaryDate,
                                   request.getPreviousPosition(),
                                   request.getCurrentPosition());
        }
    }

    private <T> void validate(final T object) {
        final Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }
    }

}
