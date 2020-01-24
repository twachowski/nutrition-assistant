package pl.polsl.wachowski.nutritionassistant.exception.entry;

public class EntryNotFoundException extends IllegalArgumentException {

    public EntryNotFoundException() {
        super("Entry has not been found");
    }

}
