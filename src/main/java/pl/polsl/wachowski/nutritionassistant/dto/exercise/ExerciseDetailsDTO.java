package pl.polsl.wachowski.nutritionassistant.dto.exercise;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.dto.user.UserBiometricsDTO;

@Value
public class ExerciseDetailsDTO {

    String name;

    Float durationMin;

    Float calories;

    UserBiometricsDTO userBiometrics;

}
