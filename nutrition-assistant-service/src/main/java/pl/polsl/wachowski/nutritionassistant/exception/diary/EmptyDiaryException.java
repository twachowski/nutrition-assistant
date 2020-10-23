package pl.polsl.wachowski.nutritionassistant.exception.diary;

public class EmptyDiaryException extends RuntimeException {

    public EmptyDiaryException(final String message) {
        super(message);
    }

}
