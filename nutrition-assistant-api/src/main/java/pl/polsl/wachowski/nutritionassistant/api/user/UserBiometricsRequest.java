package pl.polsl.wachowski.nutritionassistant.api.user;

import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Value
public class UserBiometricsRequest {

    @NotNull
    @Valid
    UserBiometrics userBiometrics;

}
