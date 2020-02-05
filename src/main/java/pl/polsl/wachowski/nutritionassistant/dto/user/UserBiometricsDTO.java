package pl.polsl.wachowski.nutritionassistant.dto.user;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometrics;
import pl.polsl.wachowski.nutritionassistant.def.activity.ActivityLevel;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class UserBiometricsDTO {

    LocalDate dateOfBirth;

    UserBiometrics.Sex sex;

    Short height;

    BigDecimal weight;

    ActivityLevel activityLevel;

}
