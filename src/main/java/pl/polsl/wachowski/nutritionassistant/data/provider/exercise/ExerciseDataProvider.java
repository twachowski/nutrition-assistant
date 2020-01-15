package pl.polsl.wachowski.nutritionassistant.data.provider.exercise;

public interface ExerciseDataProvider<T, U> {

    T search(final U request);

}
