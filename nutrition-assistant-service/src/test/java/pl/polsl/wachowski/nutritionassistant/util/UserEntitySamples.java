package pl.polsl.wachowski.nutritionassistant.util;

import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserBiometricsEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserCredentialsEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;

public final class UserEntitySamples {

    private UserEntitySamples() {
    }

    public static UserEntity inactiveUser(final String email) {
        final UserEntity user = new UserEntity(email);
        user.setUserBiometrics(UserBiometricsEntity.getDefault(user));
        user.setUserCredentials(new UserCredentialsEntity("password", user));
        return user;
    }

    public static UserEntity activeUser(final String email) {
        final UserEntity user = new UserEntity(email);
        user.activate();
        user.setUserBiometrics(UserBiometricsEntity.getDefault(user));
        user.setUserCredentials(new UserCredentialsEntity("password", user));
        return user;
    }

}
