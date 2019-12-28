package pl.polsl.wachowski.nutritionassistant.dto.user;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.validation.annotation.ValidEmail;
import pl.polsl.wachowski.nutritionassistant.validation.annotation.group.ClassCheck;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;

@Value
@GroupSequence({UserLoginDTO.class, ClassCheck.class})
public class UserLoginDTO {

    @NotBlank(message = "Email must not be blank")
    @ValidEmail(groups = ClassCheck.class)
    String email;

    @NotBlank(message = "Password must not be blank")
    String password;
}
