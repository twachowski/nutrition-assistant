package pl.polsl.wachowski.nutritionassistant.exception.token;

public class VerificationTokenExpiredException extends VerificationTokenException {

    public VerificationTokenExpiredException() {
        super("This token has expired");
    }

}
