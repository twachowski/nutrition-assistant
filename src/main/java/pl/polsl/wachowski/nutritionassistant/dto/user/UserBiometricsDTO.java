package pl.polsl.wachowski.nutritionassistant.dto.user;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometrics;
import pl.polsl.wachowski.nutritionassistant.def.activity.ActivityLevel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class UserBiometricsDTO {

    @NotNull(message = "Date of birth must not be null")
    LocalDate dateOfBirth;

    @NotNull(message = "Sex must not be null")
    UserBiometrics.Sex sex;

    @NotNull(message = "Height must not be null")
    @Positive(message = "Height must be positive")
    Short height;

    @NotNull(message = "Weight must not be null")
    @Positive(message = "Weight must be positive")
    BigDecimal weight;

    @NotNull(message = "Activity level must not be null")
    ActivityLevel activityLevel;

    @NotNull(message = "Calorie goal must not be null")
    Short calorieGoal;

}
