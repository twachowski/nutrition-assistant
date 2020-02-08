package pl.polsl.wachowski.nutritionassistant.data.provider.exercise;

import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometrics;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.ExerciseDetailsDTO;

import java.util.List;

public interface ExerciseDataProviderAdapter {

    List<ExerciseDetailsDTO> search(final String query, final UserBiometrics userBiometrics);

}
