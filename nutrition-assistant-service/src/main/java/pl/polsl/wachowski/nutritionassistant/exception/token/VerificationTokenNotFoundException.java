package pl.polsl.wachowski.nutritionassistant.exception.token;

public class VerificationTokenNotFoundException extends Exception {

    public VerificationTokenNotFoundException() {
        super("Invalid verification token");
    }

}