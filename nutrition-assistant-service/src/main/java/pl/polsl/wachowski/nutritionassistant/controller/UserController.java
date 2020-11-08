package pl.polsl.wachowski.nutritionassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.wachowski.nutritionassistant.api.user.auth.JwtTokenResponse;
import pl.polsl.wachowski.nutritionassistant.api.user.auth.UserLoginRequest;
import pl.polsl.wachowski.nutritionassistant.api.user.auth.UserRegistrationConfirmationRequest;
import pl.polsl.wachowski.nutritionassistant.api.user.auth.UserRegistrationRequest;
import pl.polsl.wachowski.nutritionassistant.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.event.RegistrationCompleteEvent;
import pl.polsl.wachowski.nutritionassistant.exception.user.UserAlreadyActiveException;
import pl.polsl.wachowski.nutritionassistant.exception.user.UserExistsException;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenException;
import pl.polsl.wachowski.nutritionassistant.service.AuthenticationService;
import pl.polsl.wachowski.nutritionassistant.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pl.polsl.wachowski.nutritionassistant.api.NutritionAssistantApi.LOGIN_API_SUFFIX;
import static pl.polsl.wachowski.nutritionassistant.api.NutritionAssistantApi.USERS_API_SUFFIX;

@RestController
@RequestMapping(USERS_API_SUFFIX)
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserController(final UserService userService,
                          final AuthenticationService authenticationService,
                          final ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> register(final HttpServletRequest httpRequest,
                                  @RequestBody @Valid final UserRegistrationRequest request) throws UserExistsException {
        final UserEntity user = userService.addUser(request.getEmail(), request.getPassword());
        eventPublisher.publishEvent(new RegistrationCompleteEvent(user, httpRequest.getHeader("origin")));
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PatchMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> confirmRegistration(@RequestBody @Valid final UserRegistrationConfirmationRequest request) throws VerificationTokenException,
                                                                                                                           UserAlreadyActiveException {
        userService.activateUser(request.getToken());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = LOGIN_API_SUFFIX,
                 consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<JwtTokenResponse> login(@RequestBody @Valid final UserLoginRequest request) {
        //TODO send credentials in Authorization header (basic auth)
        final String token = authenticationService.authenticate(request.getEmail(), request.getPassword());
        final JwtTokenResponse response = new JwtTokenResponse(token);
        return ResponseEntity.ok(response);
    }

}
