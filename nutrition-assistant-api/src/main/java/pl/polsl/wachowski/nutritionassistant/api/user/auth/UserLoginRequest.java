package pl.polsl.wachowski.nutritionassistant.api.user.auth;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.api.validation.Secondary;

import javax.validation.GroupSequence;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

@GroupSequence({UserLoginRequest.class, Secondary.class})
@Value
public class UserLoginRequest {

    @NotBlank(message = "Email must not be blank")
    String email;

    @NotBlank(message = "Password must not be blank")
    String password;

    @AssertTrue(message = "Email is not valid",
                groups = Secondary.class)
    private boolean isEmailValid() {
        return EmailUtils.isValid(email);
    }

}
