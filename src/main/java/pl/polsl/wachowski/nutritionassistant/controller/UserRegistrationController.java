package pl.polsl.wachowski.nutritionassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.wachowski.nutritionassistant.db.user.User;
import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometrics;
import pl.polsl.wachowski.nutritionassistant.db.user.UserCredentials;
import pl.polsl.wachowski.nutritionassistant.dto.user.UserRegistrationDTO;
import pl.polsl.wachowski.nutritionassistant.event.RegistrationCompleteEvent;
import pl.polsl.wachowski.nutritionassistant.exception.UserAlreadyActiveException;
import pl.polsl.wachowski.nutritionassistant.exception.UserExistsException;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenExpiredException;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenNotFoundException;
import pl.polsl.wachowski.nutritionassistant.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/user/registration")
public class UserRegistrationController {

    private final UserService userService;

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserRegistrationController(final UserService userService,
                                      final ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity register(final HttpServletRequest request,
                                   @RequestBody @Valid final UserRegistrationDTO userRegistrationDTO) {
        final User user = getUser(userRegistrationDTO);
        try {
            userService.createUser(user);
            eventPublisher.publishEvent(new RegistrationCompleteEvent(user, request.getRequestURL().toString()));

            return ResponseEntity
                    .ok()
                    .build();
        } catch (final UserExistsException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(ex.getMessage());
        }
    }

    @RequestMapping(path = "/confirm", method = RequestMethod.GET)
    public ResponseEntity confirmRegistration(@RequestParam("token") final String token) {
        try {
            userService.activateUser(token);

            return ResponseEntity
                    .ok()
                    .body("Your account has been activated!");
        } catch (final VerificationTokenNotFoundException
                | VerificationTokenExpiredException
                | UserAlreadyActiveException ex) {
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
