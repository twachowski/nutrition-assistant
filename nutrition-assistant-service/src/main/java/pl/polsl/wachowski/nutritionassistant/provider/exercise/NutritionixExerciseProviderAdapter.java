package pl.polsl.wachowski.nutritionassistant.provider.exercise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.api.exercise.Exercise;
import pl.polsl.wachowski.nutritionassistant.api.user.Sex;
import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometricsEntity;
import pl.polsl.wachowski.nutritionassistant.exception.provider.NutritionixException;
import pl.polsl.wachowski.nutritionassistant.util.DateUtil;
import pl.polsl.wachowski.nutritionix.client.NutritionixClient;
import pl.polsl.wachowski.nutritionix.client.NutritionixResult;
import pl.polsl.wachowski.nutritionix.client.api.exercise.search.NutritionixExercise;
import pl.polsl.wachowski.nutritionix.client.api.exercise.search.NutritionixExerciseSearchRequest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class NutritionixExerciseProviderAdapter implements ExerciseProvider {

    private final NutritionixClient nutritionixClient;

    @Autowired
    public NutritionixExerciseProviderAdapter(final NutritionixClient nutritionixClient) {
        this.nutritionixClient = nutritionixClient;
    }

    @Override
    public Set<Exercise> searchExercises(final String query, final UserBiometricsEntity userBiometrics) {
        final NutritionixExerciseSearchRequest searchRequest = createExerciseSearchRequest(query, userBiometrics);
        final NutritionixResult<List<NutritionixExercise>> result = nutritionixClient.searchExercises(searchRequest);
        if (result.isFailure()) {
            log.error("Failed to search exercises in Nutritionix, query={}, biometrics={}, result={}",
                      query,
                      userBiometrics,
                      result);
            throw new NutritionixException("Failed to search exercises in Nutritionix", result.exception());
        }

        return result.response()
                .stream()
                .map(NutritionixExerciseProviderAdapter::toExercise)
                .collect(Collectors.toSet());
    }

    private static NutritionixExerciseSearchRequest createExerciseSearchRequest(final String query,
                                                                                final UserBiometricsEntity userBiometrics) {
        final String gender = createGender(userBiometrics.getSex());
        final int age = DateUtil.getUserAge(userBiometrics.getDateOfBirth());
        return new NutritionixExerciseSearchRequest(query,
                                                    gender,
                                                    (short) age,
                                                    userBiometrics.getHeight(),
                                                    userBiometrics.getWeight());
    }

    private static String createGender(final Sex sex) {
        if (Sex.MALE.equals(sex)) {
            return "male";
        } else if (Sex.FEMALE.equals(sex)) {
            return "female";
        }
        return null;
    }

    private static Exercise toExercise(final NutritionixExercise exercise) {
        return new Exercise(exercise.getName(), exercise.getKcalPerMin());
    }

}
