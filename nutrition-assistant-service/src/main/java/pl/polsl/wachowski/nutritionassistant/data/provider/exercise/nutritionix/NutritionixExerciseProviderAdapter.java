package pl.polsl.wachowski.nutritionassistant.data.provider.exercise.nutritionix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.api.exercise.Exercise;
import pl.polsl.wachowski.nutritionassistant.api.user.Sex;
import pl.polsl.wachowski.nutritionassistant.data.provider.exercise.ExerciseDataProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometricsEntity;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.nutritionix.NutritionixExerciseDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.nutritionix.NutritionixExerciseSearchRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.nutritionix.NutritionixExerciseSearchResultDTO;
import pl.polsl.wachowski.nutritionassistant.util.DateUtil;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class NutritionixExerciseProviderAdapter implements ExerciseDataProviderAdapter {

    private final NutritionixExerciseProvider provider;

    @Autowired
    public NutritionixExerciseProviderAdapter(final NutritionixExerciseProvider provider) {
        this.provider = provider;
    }

    @Override
    public Set<Exercise> search(final String query, final UserBiometricsEntity userBiometrics) {
        final NutritionixExerciseSearchRequestDTO nutritionixRequest = createExerciseSearchRequest(query, userBiometrics);
        final NutritionixExerciseSearchResultDTO result = provider.search(nutritionixRequest);

        return result.getExercises()
                .stream()
                .map(NutritionixExerciseProviderAdapter::toExercise)
                .collect(Collectors.toSet());
    }

    private NutritionixExerciseSearchRequestDTO createExerciseSearchRequest(final String query,
                                                                            final UserBiometricsEntity userBiometrics) {
        final String gender = getGender(userBiometrics.getSex());
        final int age = DateUtil.getUserAge(userBiometrics.getDateOfBirth());
        return new NutritionixExerciseSearchRequestDTO(
                query,
                gender,
                (short) age,
                userBiometrics.getHeight(),
                userBiometrics.getWeight());
    }

    private static Exercise toExercise(final NutritionixExerciseDetailsDTO exerciseDetails) {
        final BigDecimal kcalPerMin = BigDecimal.valueOf(exerciseDetails.getKcalPerMin());
        return new Exercise(exerciseDetails.getName(), kcalPerMin);
    }

    private static String getGender(final Sex sex) {
        if (Sex.MALE.equals(sex)) {
            return "male";
        } else if (Sex.FEMALE.equals(sex)) {
            return "female";
        } else {
            return null;
        }
    }

}
