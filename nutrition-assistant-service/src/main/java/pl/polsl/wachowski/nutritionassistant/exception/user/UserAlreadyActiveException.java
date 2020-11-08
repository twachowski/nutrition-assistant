package pl.polsl.wachowski.nutritionassistant.exception.user;

public class UserAlreadyActiveException extends Exception {

    public UserAlreadyActiveException(final String userEmail) {
        super("User " + userEmail + " is already active");
    }

}
