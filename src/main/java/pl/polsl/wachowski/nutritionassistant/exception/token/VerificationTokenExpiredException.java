package pl.polsl.wachowski.nutritionassistant.exception.token;

public class VerificationTokenExpiredException extends Exception {

    public VerificationTokenExpiredException(final String message) {
        super(message);
    }

}
