package pl.polsl.wachowski.nutritionassistant.data.provider.exercise.nutritionix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.data.provider.exercise.ExerciseDataProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometrics;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.ExerciseDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.ExerciseSearchRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.nutritionix.NutritionixExerciseDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.nutritionix.NutritionixExerciseSearchRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.nutritionix.NutritionixExerciseSearchResultDTO;
import pl.polsl.wachowski.nutritionassistant.dto.user.UserBiometricsDTO;
import pl.polsl.wachowski.nutritionassistant.service.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NutritionixExerciseProviderAdapter implements ExerciseDataProviderAdapter {

    private final NutritionixExerciseProvider provider;

    private final UserService userService;

    @Autowired
    public NutritionixExerciseProviderAdapter(final NutritionixExerciseProvider provider,
                                              final UserService userService) {
        this.provider = provider;
        this.userService = userService;
    }

    @Override
    public List<ExerciseDetailsDTO> search(final ExerciseSearchRequestDTO request) {
        final UserBiometrics userBiometrics = userService.getUserBiometrics(request.getUser());
        final NutritionixExerciseSearchRequestDTO nutritionixRequest =
                createExerciseSearchRequest(request, userBiometrics);
        final NutritionixExerciseSearchResultDTO result = provider.search(nutritionixRequest);

        final UserBiometricsDTO biometrics = mapBiometrics(userBiometrics);
        return result.getExercises()
                .stream()
                .map(details -> toExerciseDetails(details, biometrics))
                .collect(Collectors.toList());
    }

    private NutritionixExerciseSearchRequestDTO createExerciseSearchRequest(final ExerciseSearchRequestDTO request,
                                                                            final UserBiometrics userBiometrics) {
        final String gender = getGender(userBiometrics.getSex());
        final Integer age = getAge(userBiometrics.getDateOfBirth());
        return new NutritionixExerciseSearchRequestDTO(
                request.getQuery(),
                gender,
                age.shortValue(),
                userBiometrics.getHeight(),
                userBiometrics.getWeight());
    }

    private static ExerciseDetailsDTO toExerciseDetails(final NutritionixExerciseDetailsDTO exerciseDetails,
                                                        final UserBiometricsDTO userBiometrics) {
        return new ExerciseDetailsDTO(
                exerciseDetails.getName(),
                exerciseDetails.getDuration(),
                exerciseDetails.getCalories(),
                userBiometrics);
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

    private static Integer getAge(final LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    private static UserBiometricsDTO mapBiometrics(final UserBiometrics userBiometrics) {
        final Integer age = getAge(userBiometrics.getDateOfBirth());
        return new UserBiometricsDTO(
                age.shortValue(),
                userBiometrics.getSex(),
                userBiometrics.getHeight(),
                userBiometrics.getWeight());
    }

}
