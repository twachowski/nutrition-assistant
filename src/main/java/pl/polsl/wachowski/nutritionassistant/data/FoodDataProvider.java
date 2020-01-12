package pl.polsl.wachowski.nutritionassistant.data;

public interface FoodDataProvider<T> {

    T search(final String query);

}
