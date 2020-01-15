package pl.polsl.wachowski.nutritionassistant.dto.user;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometrics;

import java.math.BigDecimal;

@Value
public class UserBiometricsDTO {

    Short age;

    UserBiometrics.Sex sex;

    Short height;

    BigDecimal weight;

}
