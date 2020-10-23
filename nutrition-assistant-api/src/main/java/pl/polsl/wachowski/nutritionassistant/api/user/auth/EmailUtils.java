package pl.polsl.wachowski.nutritionassistant.api.user.auth;

import java.util.regex.Pattern;

final class EmailUtils {

    private static final String EMAIL_PATTERN = "[^@]+@[^@]+\\.[\\w]+";

    static boolean isValid(final String email) {
        return Pattern.compile(EMAIL_PATTERN)
                .matcher(email)
                .matches();
    }

}
