package pl.polsl.wachowski.nutritionassistant.exception;

import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {

    public UserNotFoundException() {
        super("User has not been found");
    }

}
