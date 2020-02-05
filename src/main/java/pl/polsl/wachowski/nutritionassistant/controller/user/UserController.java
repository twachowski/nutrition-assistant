package pl.polsl.wachowski.nutritionassistant.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.wachowski.nutritionassistant.dto.user.UserBiometricsDTO;
import pl.polsl.wachowski.nutritionassistant.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(
            path = "/biometrics",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserBiometrics() {
        final UserBiometricsDTO userBiometrics = userService.getUserBiometricsDTO();
        return ResponseEntity.ok(userBiometrics);
    }

    @RequestMapping(
            path = "/biometrics",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUserBiometrics(@RequestBody @Valid final UserBiometricsDTO userBiometrics) {
        userService.saveUserBiometrics(userBiometrics);

        return ResponseEntity
                .ok()
                .build();
    }

}
