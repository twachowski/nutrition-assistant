package pl.polsl.wachowski.nutritionassistant.exception;

import org.springframework.security.core.AuthenticationException;

public class UserInactiveException extends AuthenticationException {

    public UserInactiveException() {
        super("This account has not been yet activated");
    }

}
