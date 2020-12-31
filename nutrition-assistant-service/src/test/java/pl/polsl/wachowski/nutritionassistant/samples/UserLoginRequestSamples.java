package pl.polsl.wachowski.nutritionassistant.samples;

import pl.polsl.wachowski.nutritionassistant.api.user.auth.UserLoginRequest;

public final class UserLoginRequestSamples {

    private UserLoginRequestSamples() {
    }

    public static UserLoginRequest withNullEmail() {
        return new UserLoginRequest(null,
                                    "validPassword");
    }

    public static UserLoginRequest withEmptyEmail() {
        return new UserLoginRequest("",
                                    "validPassword");
    }

    public static UserLoginRequest withBlankEmail() {
        return new UserLoginRequest("   ",
                                    "validPassword");
    }

    public static UserLoginRequest withNoAtSignInEmail() {
        return new UserLoginRequest("foo.bar.com",
                                    "validPassword");
    }

    public static UserLoginRequest withTwoAtSignsInEmail() {
        return new UserLoginRequest("foo@bar@bar.com",
                                    "validPassword");
    }

    public static UserLoginRequest withNoDotInEmail() {
        return new UserLoginRequest("foo@bar",
                                    "validPassword");
    }

    public static UserLoginRequest withDotAsLastCharacterInEmail() {
        return new UserLoginRequest("foo@bar.",
                                    "validPassword");
    }

    public static UserLoginRequest withNullPassword() {
        return new UserLoginRequest("foo@bar.com",
                                    null);
    }

    public static UserLoginRequest withEmptyPassword() {
        return new UserLoginRequest("foo@bar.com",
                                    "");
    }

    public static UserLoginRequest withBlankPassword() {
        return new UserLoginRequest("foo@bar.com",
                                    "  ");
    }

    public static UserLoginRequest valid() {
        return new UserLoginRequest("foo@bar.com",
                                    "validPassword");
    }

}
