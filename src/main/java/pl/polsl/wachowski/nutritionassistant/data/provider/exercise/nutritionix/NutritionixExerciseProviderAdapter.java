package pl.polsl.wachowski.nutritionassistant.data.provider.exercise.nutritionix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.data.provider.exercise.ExerciseDataProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometrics;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.ExerciseDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.nutritionix.NutritionixExerciseDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.nutritionix.NutritionixExerciseSearchRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.nutritionix.NutritionixExerciseSearchResultDTO;
import pl.polsl.wachowski.nutritionassistant.util.DateUtil;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NutritionixExerciseProviderAdapter implements ExerciseDataProviderAdapter {

    private final NutritionixExerciseProvider provider;

    @Autowired
    public NutritionixExerciseProviderAdapter(final NutritionixExerciseProvider provider) {
        this.provider = provider;
    }

    @Override
    public List<ExerciseDetailsDTO> search(final String query, final UserBiometrics userBiometrics) {
        final NutritionixExerciseSearchRequestDTO nutritionixRequest = createExerciseSearchRequest(query, userBiometrics);
        final NutritionixExerciseSearchResultDTO result = provider.search(nutritionixRequest);

        return result.getExercises()
                .stream()
                .map(NutritionixExerciseProviderAdapter::toExerciseDetails)
                .collect(Collectors.toList());
    }

    private NutritionixExerciseSearchRequestDTO createExerciseSearchRequest(final String query,
                                                                            final UserBiometrics userBiometrics) {
        final String gender = getGender(userBiometrics.getSex());
        final Integer age = DateUtil.getUserAge(userBiometrics.getDateOfBirth());
        return new NutritionixExerciseSearchRequestDTO(
                query,
                gender,
                age.shortValue(),
                userBiometrics.getHeight(),
                userBiometrics.getWeight());
    }

    private static ExerciseDetailsDTO toExerciseDetails(final NutritionixExerciseDetailsDTO exerciseDetails) {
        return new ExerciseDetailsDTO(
                exerciseDetails.getName(),
                exerciseDetails.getDuration(),
                exerciseDetails.getCalories());
    }

    private static String getGender(final UserBiometrics.Sex sex) {
        if (UserBiometrics.Sex.M.equals(sex)) {
            return "male";
        } else if (UserBiometrics.Sex.F.equals(sex)) {
            return "female";
        } else {
            return null;
        }
    }

}
