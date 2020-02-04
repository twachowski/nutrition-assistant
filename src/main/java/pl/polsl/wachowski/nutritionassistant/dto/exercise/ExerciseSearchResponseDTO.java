package pl.polsl.wachowski.nutritionassistant.dto.exercise;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.dto.user.UserSimpleBiometricsDTO;

import java.util.List;

@Value
public class ExerciseSearchResponseDTO {

    UserSimpleBiometricsDTO userBiometrics;

    List<ExerciseDetailsDTO> exercises;

}
