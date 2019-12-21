package pl.polsl.wachowski.nutritionassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.wachowski.nutritionassistant.db.user.User;
import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometrics;
import pl.polsl.wachowski.nutritionassistant.db.user.UserCredentials;
import pl.polsl.wachowski.nutritionassistant.dto.user.UserRegistrationDTO;
import pl.polsl.wachowski.nutritionassistant.exception.UserExistsException;
import pl.polsl.wachowski.nutritionassistant.service.UserService;

import javax.validation.Valid;

@RestController
public class UserRegistrationController {

    private final UserService userService;

    @Autowired
    public UserRegistrationController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/user/registration", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody @Valid final UserRegistrationDTO userRegistrationDTO) {
        final User user = getUser(userRegistrationDTO);
        try {
            userService.createUser(user);
            return ResponseEntity
                    .ok()
                    .build();
        } catch (final UserExistsException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(ex.getMessage());
        }
    }

    private static User getUser(final UserRegistrationDTO userRegistrationDTO) {
        final User user = new User(userRegistrationDTO.getEmail());

        final UserBiometrics userBiometrics = UserBiometrics.getDefault(user);
        user.setUserBiometrics(userBiometrics);

        final UserCredentials userCredentials = new UserCredentials(userRegistrationDTO.getPassword(), user);
        user.setUserCredentials(userCredentials);

        return user;
    }

}
