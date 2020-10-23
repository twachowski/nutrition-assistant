package pl.polsl.wachowski.nutritionassistant.util;

import java.time.LocalDate;
import java.time.Period;

public final class DateUtil {

    private DateUtil() {
    }

    public static int getUserAge(final LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

}
