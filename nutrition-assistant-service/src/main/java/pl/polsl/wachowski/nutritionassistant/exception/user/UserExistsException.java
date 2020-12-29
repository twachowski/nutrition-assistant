package pl.polsl.wachowski.nutritionassistant.exception.user;

public class UserExistsException extends Exception {

    public UserExistsException() {
        super("User with given email already exists");
    }

}
