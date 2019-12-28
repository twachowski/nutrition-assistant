package pl.polsl.wachowski.nutritionassistant.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidCredentialsException extends AuthenticationException {

    public InvalidCredentialsException() {
        super("Invalid credentials have been provided");
    }

}
