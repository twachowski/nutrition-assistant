package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.api.user.UserBiometrics;
import pl.polsl.wachowski.nutritionassistant.api.user.UserSimpleBiometrics;
import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometricsEntity;
import pl.polsl.wachowski.nutritionassistant.exception.UserNotFoundException;
import pl.polsl.wachowski.nutritionassistant.repository.UserBiometricsRepository;
import pl.polsl.wachowski.nutritionassistant.util.DateUtil;

@Service
public class ProfileService {

    private final UserBiometricsRepository userBiometricsRepository;
    private final AuthenticationService authenticationService;

    @Autowired
    public ProfileService(final UserBiometricsRepository userBiometricsRepository,
                          final AuthenticationService authenticationService) {
        this.userBiometricsRepository = userBiometricsRepository;
        this.authenticationService = authenticationService;
    }

    public UserBiometrics getAuthenticatedUserBiometrics() {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        return userBiometricsRepository.findUserBiometricsByUserEmail(userEmail)
                .map(ProfileService::toUserBiometrics)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserBiometricsEntity getAuthenticatedUserBiometricsEntity() {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        return userBiometricsRepository.findUserBiometricsByUserEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
    }

    public void updateUserBiometrics(final UserBiometrics newUserBiometrics) {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        final UserBiometricsEntity userBiometrics = userBiometricsRepository.findUserBiometricsByUserEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
        userBiometrics.setDateOfBirth(newUserBiometrics.getDateOfBirth());
        userBiometrics.setSex(newUserBiometrics.getSex());
        userBiometrics.setHeight(newUserBiometrics.getHeight());
        userBiometrics.setWeight(newUserBiometrics.getWeight());
        userBiometrics.setActivityLevel(newUserBiometrics.getActivityLevel());
        userBiometrics.setCalorieGoal(newUserBiometrics.getCalorieGoal());

        userBiometricsRepository.save(userBiometrics);
    }

    public static UserSimpleBiometrics toSimpleBiometrics(final UserBiometricsEntity userBiometricsEntity) {
        final int age = DateUtil.getUserAge(userBiometricsEntity.getDateOfBirth());
        return new UserSimpleBiometrics((short) age,
                                        userBiometricsEntity.getSex(),
                                        userBiometricsEntity.getHeight(),
                                        userBiometricsEntity.getWeight());
    }

    private static UserBiometrics toUserBiometrics(final UserBiometricsEntity userBiometricsEntity) {
        return new UserBiometrics(userBiometricsEntity.getDateOfBirth(),
                                  userBiometricsEntity.getSex(),
                                  userBiometricsEntity.getHeight(),
                                  userBiometricsEntity.getWeight(),
                                  userBiometricsEntity.getActivityLevel(),
                                  userBiometricsEntity.getCalorieGoal());
    }

}
