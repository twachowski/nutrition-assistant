package pl.polsl.wachowski.nutritionassistant.validation;

import pl.polsl.wachowski.nutritionassistant.validation.annotation.ValidPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final int PASSWORD_LENGTH = 12;

    private static final String PASSWORD_TOO_SHORT_MSG =
            String.format("Password should be at least %d characters long", PASSWORD_LENGTH);

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext constraintValidatorContext) {
        if (password.length() >= PASSWORD_LENGTH) {
            return true;
        }

        constraintValidatorContext.disableDefaultConstraintViolation();

        constraintValidatorContext
                .buildConstraintViolationWithTemplate(PASSWORD_TOO_SHORT_MSG)
                .addConstraintViolation();

        return false;
    }

}
