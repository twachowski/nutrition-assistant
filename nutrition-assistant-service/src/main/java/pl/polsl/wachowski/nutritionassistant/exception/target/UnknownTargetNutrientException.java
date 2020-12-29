package pl.polsl.wachowski.nutritionassistant.exception.target;

public class UnknownTargetNutrientException extends Exception {

    public UnknownTargetNutrientException(final String nutrientName) {
        super("Unknown target nutrient name: " + nutrientName);
    }

}
