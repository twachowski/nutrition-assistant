package pl.polsl.wachowski.nutritionassistant.exception;

public class UserAlreadyActiveException extends Exception {

    public UserAlreadyActiveException() {
        super("Your account has been already activated");
    }

}
