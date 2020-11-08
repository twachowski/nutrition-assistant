package pl.polsl.wachowski.nutritionassistant.exception.user;

public class UserExistsException extends Exception {

    public UserExistsException(final String userEmail) {
        super("User " + userEmail + " already exists");
    }

}
