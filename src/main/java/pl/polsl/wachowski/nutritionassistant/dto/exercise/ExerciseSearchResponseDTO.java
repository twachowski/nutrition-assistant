package pl.polsl.wachowski.nutritionassistant.dto.exercise;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.dto.user.UserBiometricsDTO;

import java.util.List;

@Value
public class ExerciseSearchResponseDTO {

    UserBiometricsDTO userBiometrics;

    List<ExerciseDetailsDTO> exercises;

}
