package pl.polsl.wachowski.nutritionassistant.data.provider;

public interface FoodDataProvider<T, U> {

    T search(final String query);

    U getDetails(final String id);

}
