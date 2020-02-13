package pl.polsl.wachowski.nutritionassistant.exception.token;

public class VerificationTokenExpiredException extends Exception {

    public VerificationTokenExpiredException() {
        super("This token has expired");
    }

}
