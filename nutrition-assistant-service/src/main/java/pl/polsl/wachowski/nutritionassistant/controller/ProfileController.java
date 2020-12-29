package pl.polsl.wachowski.nutritionassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.wachowski.nutritionassistant.api.user.UserBiometrics;
import pl.polsl.wachowski.nutritionassistant.api.user.UserBiometricsRequest;
import pl.polsl.wachowski.nutritionassistant.api.user.UserBiometricsResponse;
import pl.polsl.wachowski.nutritionassistant.service.ProfileService;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pl.polsl.wachowski.nutritionassistant.api.NutritionAssistantApi.BIOMETRICS_API_SUFFIX;
import static pl.polsl.wachowski.nutritionassistant.api.NutritionAssistantApi.PROFILE_API_SUFFIX;

@RestController
@RequestMapping(PROFILE_API_SUFFIX)
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(final ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(path = BIOMETRICS_API_SUFFIX,
                produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UserBiometricsResponse> getBiometrics() {
        final UserBiometrics userBiometrics = profileService.getAuthenticatedUserBiometrics();
        final UserBiometricsResponse response = new UserBiometricsResponse(userBiometrics);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = BIOMETRICS_API_SUFFIX,
                consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateBiometrics(@RequestBody @Valid final UserBiometricsRequest request) {
        profileService.updateUserBiometrics(request.getUserBiometrics());
        return ResponseEntity.noContent().build();
    }

}
