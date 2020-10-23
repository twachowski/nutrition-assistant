package pl.polsl.wachowski.nutritionassistant.data.provider.exercise;

import pl.polsl.wachowski.nutritionassistant.api.exercise.Exercise;
import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometricsEntity;

import java.util.Set;

public interface ExerciseDataProviderAdapter {

    Set<Exercise> search(final String query, final UserBiometricsEntity userBiometrics);

}
