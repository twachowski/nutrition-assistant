package pl.polsl.wachowski.nutritionassistant.provider.exercise;

import pl.polsl.wachowski.nutritionassistant.api.exercise.Exercise;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserBiometricsEntity;

import java.util.Set;

public interface ExerciseProvider {

    Set<Exercise> searchExercises(String query, UserBiometricsEntity userBiometrics);

}
