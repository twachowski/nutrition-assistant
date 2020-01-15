package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.data.provider.exercise.ExerciseDataProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometrics;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.ExerciseDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.ExerciseSearchRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.ExerciseSearchResponseDTO;
import pl.polsl.wachowski.nutritionassistant.dto.user.UserBiometricsDTO;
import pl.polsl.wachowski.nutritionassistant.util.DateUtil;

import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseDataProviderAdapter provider;

    private final UserService userService;

    @Autowired
    public ExerciseService(final ExerciseDataProviderAdapter provider, final UserService userService) {
        this.provider = provider;
        this.userService = userService;
    }

    public ExerciseSearchResponseDTO search(final ExerciseSearchRequestDTO request) {
        final UserBiometrics userBiometrics = userService.getUserBiometrics(request.getUser());
        final List<ExerciseDetailsDTO> exercises = provider.search(request, userBiometrics);

        final UserBiometricsDTO biometricsDTO = mapUserBiometrics(userBiometrics);
        return new ExerciseSearchResponseDTO(biometricsDTO, exercises);
    }

    private static UserBiometricsDTO mapUserBiometrics(final UserBiometrics userBiometrics) {
        final Integer age = DateUtil.getUserAge(userBiometrics.getDateOfBirth());
        return new UserBiometricsDTO(
                age.shortValue(),
                userBiometrics.getSex(),
                userBiometrics.getHeight(),
                userBiometrics.getWeight());
    }

}
