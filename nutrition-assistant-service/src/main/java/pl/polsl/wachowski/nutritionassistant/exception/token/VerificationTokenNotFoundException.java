package pl.polsl.wachowski.nutritionassistant.exception.token;

public class VerificationTokenNotFoundException extends VerificationTokenException {

    public VerificationTokenNotFoundException() {
        super("Invalid verification token");
    }

}
