package pl.polsl.wachowski.nutritionassistant.api.user.auth;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class UserRegistrationConfirmationRequest {

    @NotBlank
    String token;

}
