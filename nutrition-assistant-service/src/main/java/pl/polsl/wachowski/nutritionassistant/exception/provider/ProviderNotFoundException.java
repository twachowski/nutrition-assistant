package pl.polsl.wachowski.nutritionassistant.exception.provider;


import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;

public class ProviderNotFoundException extends RuntimeException {

    public ProviderNotFoundException(final NutritionDataProvider provider) {
        super(String.format("Provider '%s' has not been found", provider));
    }

}
