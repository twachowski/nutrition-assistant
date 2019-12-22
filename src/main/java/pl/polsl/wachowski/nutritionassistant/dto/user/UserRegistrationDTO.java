package pl.polsl.wachowski.nutritionassistant.dto.user;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.validation.annotation.PasswordMatch;
import pl.polsl.wachowski.nutritionassistant.validation.annotation.ValidEmail;
import pl.polsl.wachowski.nutritionassistant.validation.annotation.ValidPassword;
import pl.polsl.wachowski.nutritionassistant.validation.annotation.group.ClassCheck;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;

@Value
@GroupSequence({UserRegistrationDTO.class, ClassCheck.class})
@PasswordMatch(groups = ClassCheck.class)
public class UserRegistrationDTO {

    @NotBlank(message = "Email must not be blank")
    @ValidEmail(groups = ClassCheck.class)
    String email;

    @NotBlank(message = "Password must not be blank")
    @ValidPassword(groups = ClassCheck.class)
    String password;

    String confirmedPassword;

}
