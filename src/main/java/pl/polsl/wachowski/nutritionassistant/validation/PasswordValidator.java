package pl.polsl.wachowski.nutritionassistant.validation;

import pl.polsl.wachowski.nutritionassistant.dto.user.UserRegistrationDTO;
import pl.polsl.wachowski.nutritionassistant.validation.annotation.PasswordMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordMatch, UserRegistrationDTO> {

    @Override
    public boolean isValid(final UserRegistrationDTO user, final ConstraintValidatorContext context) {
        return user.getPassword().equals(user.getConfirmedPassword());
    }

}
