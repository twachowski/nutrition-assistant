package pl.polsl.wachowski.nutritionassistant.samples;

import pl.polsl.wachowski.nutritionassistant.api.user.ActivityLevel;
import pl.polsl.wachowski.nutritionassistant.api.user.Sex;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserBiometricsEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class UserBiometricsEntitySamples {

    private UserBiometricsEntitySamples() {
    }

    public static UserBiometricsEntity shortFemaleOfGivenAge(final short age) {
        final LocalDate birthDate = LocalDate.now()
                .minusYears(age)
                .minusDays(1);
        return new UserBiometricsEntity(birthDate,
                                        Sex.FEMALE,
                                        (short) 160,
                                        new BigDecimal("55.21"),
                                        ActivityLevel.HIGH,
                                        (short) 2500,
                                        null);
    }

}
