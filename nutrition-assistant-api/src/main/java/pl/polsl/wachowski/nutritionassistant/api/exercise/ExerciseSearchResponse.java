package pl.polsl.wachowski.nutritionassistant.api.exercise;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.api.user.UserSimpleBiometrics;

import java.util.Set;

@Value
public class ExerciseSearchResponse {

    UserSimpleBiometrics userSimpleBiometrics;
    Set<Exercise> exercises;

}
