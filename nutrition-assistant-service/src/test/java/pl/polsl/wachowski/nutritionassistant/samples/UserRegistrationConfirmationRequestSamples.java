package pl.polsl.wachowski.nutritionassistant.samples;

import pl.polsl.wachowski.nutritionassistant.api.user.auth.UserRegistrationConfirmationRequest;

public final class UserRegistrationConfirmationRequestSamples {

    private UserRegistrationConfirmationRequestSamples() {
    }

    public static UserRegistrationConfirmationRequest withNullToken() {
        return new UserRegistrationConfirmationRequest(null);
    }

    public static UserRegistrationConfirmationRequest withEmptyToken() {
        return new UserRegistrationConfirmationRequest("");
    }

    public static UserRegistrationConfirmationRequest withBlankToken() {
        return new UserRegistrationConfirmationRequest("  ");
    }

    public static UserRegistrationConfirmationRequest notBlank() {
        return new UserRegistrationConfirmationRequest("ahweufjaowiejfawioefj");
    }

}
