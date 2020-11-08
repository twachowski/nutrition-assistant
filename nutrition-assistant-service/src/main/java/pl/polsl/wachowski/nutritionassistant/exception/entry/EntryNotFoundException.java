package pl.polsl.wachowski.nutritionassistant.exception.entry;

import java.time.LocalDate;

public class EntryNotFoundException extends RuntimeException {

    public EntryNotFoundException(final String message) {
        super(message);
    }

    public static EntryNotFoundException emptyDiary(final LocalDate diaryDate) {
        return new EntryNotFoundException("Diary is empty for date " + diaryDate);
    }

}
