package pl.polsl.wachowski.nutritionassistant.validation;

import pl.polsl.wachowski.nutritionassistant.validation.annotation.ValidEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final String EMAIL_PATTERN = "[^@]+@[^@]+\\.[\\w]+";

    @Override
    public boolean isValid(final String email, final ConstraintValidatorContext constraintValidatorContext) {
        return Pattern
                .compile(EMAIL_PATTERN)
                .matcher(email)
                .matches();
    }

}
