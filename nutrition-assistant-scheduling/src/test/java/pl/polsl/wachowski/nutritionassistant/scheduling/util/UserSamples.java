package pl.polsl.wachowski.nutritionassistant.scheduling.util;

import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserBiometricsEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserCredentialsEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;

public final class UserSamples {

    private UserSamples() {
    }

    public static UserEntity activeUser() {
        final UserEntity userEntity = new UserEntity("foo@bar.com");
        userEntity.activate();
        userEntity.setUserBiometrics(UserBiometricsEntity.getDefault(userEntity));
        userEntity.setUserCredentials(new UserCredentialsEntity("password", userEntity));
        return userEntity;
    }

    public static UserEntity inactiveUser() {
        final UserEntity userEntity = new UserEntity("bar@foo.com");
        userEntity.setUserBiometrics(UserBiometricsEntity.getDefault(userEntity));
        userEntity.setUserCredentials(new UserCredentialsEntity("password", userEntity));
        return userEntity;
    }

}
