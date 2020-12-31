package pl.polsl.wachowski.nutritionassistant.samples;

import pl.polsl.wachowski.nutritionassistant.api.user.auth.UserRegistrationRequest;

public final class UserRegistrationRequestSamples {

    private UserRegistrationRequestSamples() {
    }

    public static UserRegistrationRequest valid() {
        return new UserRegistrationRequest("foo@bar.com",
                                           "validPassword",
                                           "validPassword");
    }

    public static UserRegistrationRequest withNullEmail() {
        return new UserRegistrationRequest(null,
                                           "validPassword",
                                           "validPassword");
    }

    public static UserRegistrationRequest withEmptyEmail() {
        return new UserRegistrationRequest("",
                                           "validPassword",
                                           "validPassword");
    }

    public static UserRegistrationRequest withBlankEmail() {
        return new UserRegistrationRequest("    ",
                                           "validPassword",
                                           "validPassword");
    }

    public static UserRegistrationRequest withNoAtSignInEmail() {
        return new UserRegistrationRequest("foo.bar.com",
                                           "validPassword",
                                           "validPassword");
    }

    public static UserRegistrationRequest withTwoAtSignsInEmail() {
        return new UserRegistrationRequest("foo@bar@bar.com",
                                           "validPassword",
                                           "validPassword");
    }

    public static UserRegistrationRequest withNoDotInEmail() {
        return new UserRegistrationRequest("foo@bar",
                                           "validPassword",
                                           "validPassword");
    }

    public static UserRegistrationRequest withDotAsLastCharacterInEmail() {
        return new UserRegistrationRequest("foo@bar.",
                                           "validPassword",
                                           "validPassword");
    }

    public static UserRegistrationRequest withNullPassword() {
        return new UserRegistrationRequest("foo@bar.com",
                                           null,
                                           "validPassword");
    }

    public static UserRegistrationRequest withEmptyPassword() {
        return new UserRegistrationRequest("foo@bar.com",
                                           "",
                                           "validPassword");
    }

    public static UserRegistrationRequest withBlankPassword() {
        return new UserRegistrationRequest("foo@bar.com",
                                           "   ",
                                           "validPassword");
    }

    public static UserRegistrationRequest withTooShortPassword() {
        return new UserRegistrationRequest("foo@bar.com",
                                           "password",
                                           "password");
    }

    public static UserRegistrationRequest withNullPasswordConfirmation() {
        return new UserRegistrationRequest("foo@bar.com",
                                           "validPassword",
                                           null);
    }

    public static UserRegistrationRequest withEmptyPasswordConfirmation() {
        return new UserRegistrationRequest("foo@bar.com",
                                           "validPassword",
                                           "");
    }

    public static UserRegistrationRequest withBlankPasswordConfirmation() {
        return new UserRegistrationRequest("foo@bar.com",
                                           "validPassword",
                                           "    ");
    }

    public static UserRegistrationRequest withDifferentPasswordAndConfirmation() {
        return new UserRegistrationRequest("foo@bar.com",
                                           "validPassword",
                                           "ValidPassword");
    }

}
