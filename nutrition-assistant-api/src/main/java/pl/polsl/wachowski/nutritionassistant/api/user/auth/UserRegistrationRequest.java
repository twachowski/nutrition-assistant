package pl.polsl.wachowski.nutritionassistant.api.user.auth;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.api.validation.Secondary;

import javax.validation.GroupSequence;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@GroupSequence({UserRegistrationRequest.class, Secondary.class})
@Value
public class UserRegistrationRequest {

    @NotBlank(message = "Email must not be blank")
    String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 12, message = "Password should be at least 12 characters long")
    String password;

    @NotBlank(message = "Password confirmation must not be blank")
    String passwordConfirmation;

    @AssertTrue(message = "Email is not valid",
                groups = Secondary.class)
    private boolean isValidEmail() {
        return EmailUtils.isValid(email);
    }

    @AssertTrue(message = "Passwords do not match",
                groups = Secondary.class)
    private boolean isPasswordConfirmationValid() {
        return password.equals(passwordConfirmation);
    }

}
