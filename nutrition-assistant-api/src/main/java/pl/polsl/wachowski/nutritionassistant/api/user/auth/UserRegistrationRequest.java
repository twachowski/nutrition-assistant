package pl.polsl.wachowski.nutritionassistant.api.user.auth;

import lombok.Value;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
public class UserRegistrationRequest {

    @NotBlank
    String email;

    @NotBlank
    @Size(min = 12, message = "Password should be at least 12 characters long")
    String password;

    @NotBlank
    String passwordConfirmation;

    @AssertTrue(message = "Email is not valid")
    private boolean isValidEmail() {
        return EmailUtils.isValid(email);
    }

    @AssertTrue(message = "Passwords do not match")
    private boolean isPasswordConfirmationValid() {
        return password.equals(passwordConfirmation);
    }

}
