package pl.polsl.wachowski.nutritionassistant.validation;

import pl.polsl.wachowski.nutritionassistant.dto.UserDTO;
import pl.polsl.wachowski.nutritionassistant.validation.annotation.PasswordMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordMatch, UserDTO> {

    @Override
    public boolean isValid(final UserDTO user, final ConstraintValidatorContext context) {
        return user.getPassword().equals(user.getConfirmedPassword());
    }

}
