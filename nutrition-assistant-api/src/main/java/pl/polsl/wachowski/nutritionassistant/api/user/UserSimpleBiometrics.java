package pl.polsl.wachowski.nutritionassistant.api.user;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class UserSimpleBiometrics {

    Short age;
    Sex sex;
    Short height;
    BigDecimal weight;

}
