package pl.polsl.wachowski.nutritionassistant.api.user.auth;

import lombok.Value;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

@Value
public class UserLoginRequest {

    @NotBlank
    String email;

    @NotBlank
    String password;

    @AssertTrue(message = "Email is not valid")
    private boolean isEmailValid() {
        return EmailUtils.isValid(email);
    }

}
