package pl.polsl.wachowski.nutritionassistant.api.user;

import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class UserBiometrics {

    @NotNull
    LocalDate dateOfBirth;

    @NotNull
    Sex sex;

    @NotNull
    @Positive
    Short height;

    @NotNull
    @Positive
    BigDecimal weight;

    @NotNull
    ActivityLevel activityLevel;

    @NotNull
    Short calorieGoal;

}
