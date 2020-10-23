package pl.polsl.wachowski.nutritionassistant.api.user.auth;

import lombok.Value;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

@Value
public class UserRegistrationRequest {

    @NotBlank
    String email;

    @NotBlank
    String password;

    @NotBlank
    String passwordConfirmation;

    @AssertTrue(message = "Email is not valid")
    private boolean isValidEmail() {
        return EmailUtils.isValid(email);
    }

    @AssertTrue(message = "Passwords do not match")
    private boolean passwordsMatch() {
        return password.equals(passwordConfirmation);
    }

}
