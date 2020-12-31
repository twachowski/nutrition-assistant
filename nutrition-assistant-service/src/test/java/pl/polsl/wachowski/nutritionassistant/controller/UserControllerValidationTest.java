package pl.polsl.wachowski.nutritionassistant.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pl.polsl.wachowski.nutritionassistant.api.user.auth.UserLoginRequest;
import pl.polsl.wachowski.nutritionassistant.api.user.auth.UserRegistrationConfirmationRequest;
import pl.polsl.wachowski.nutritionassistant.api.user.auth.UserRegistrationRequest;
import pl.polsl.wachowski.nutritionassistant.exception.user.UserExistsException;
import pl.polsl.wachowski.nutritionassistant.samples.UserEntitySamples;
import pl.polsl.wachowski.nutritionassistant.samples.UserLoginRequestSamples;
import pl.polsl.wachowski.nutritionassistant.samples.UserRegistrationConfirmationRequestSamples;
import pl.polsl.wachowski.nutritionassistant.samples.UserRegistrationRequestSamples;
import pl.polsl.wachowski.nutritionassistant.service.AuthenticationService;
import pl.polsl.wachowski.nutritionassistant.service.UserService;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.polsl.wachowski.nutritionassistant.api.NutritionAssistantApi.*;

@WebMvcTest(value = UserController.class,
            excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {
        UserController.class,
        ControllerExceptionHandler.class
})
class UserControllerValidationTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;
    @MockBean
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() throws UserExistsException {
        Mockito.when(userService.addUser("foo@bar.com", "validPassword"))
                .thenReturn(UserEntitySamples.inactiveUser("foo@bar.com"));
        Mockito.when(authenticationService.authenticate("foo@bar.com", "validPassword"))
                .thenReturn("mock-token");
    }

    @Test
    @DisplayName("Should fail validation when trying to send null user registration request")
    void shouldFailValidationWhenTryingToSendNullUserRegistrationRequest() throws Exception {
        //given

        //when

        //then
        mvc.perform(post(USERS_API_SUFFIX)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to register user with null email")
    void shouldFailValidationWhenTryingToRegisterUserWithNullEmail() throws Exception {
        //given
        final UserRegistrationRequest request = UserRegistrationRequestSamples.withNullEmail();

        //when

        //then
        mvc.perform(post(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to register user with empty email")
    void shouldFailValidationWhenTryingToRegisterUserWithEmptyEmail() throws Exception {
        //given
        final UserRegistrationRequest request = UserRegistrationRequestSamples.withEmptyEmail();

        //when

        //then
        mvc.perform(post(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to register user with blank email")
    void shouldFailValidationWhenTryingToRegisterUserWithBlankEmail() throws Exception {
        //given
        final UserRegistrationRequest request = UserRegistrationRequestSamples.withBlankEmail();

        //when

        //then
        mvc.perform(post(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to register user with no '@' sign in email")
    void shouldFailValidationWhenTryingToRegisterUserWithNoAtSignInEmail() throws Exception {
        //given
        final UserRegistrationRequest request = UserRegistrationRequestSamples.withNoAtSignInEmail();

        //when

        //then
        mvc.perform(post(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email is not valid"));
    }

    @Test
    @DisplayName("Should fail validation when trying to register user with two '@' signs in email")
    void shouldFailValidationWhenTryingToRegisterUserWithTwoAtSignsInEmail() throws Exception {
        //given
        final UserRegistrationRequest request = UserRegistrationRequestSamples.withTwoAtSignsInEmail();

        //when

        //then
        mvc.perform(post(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email is not valid"));
    }

    @Test
    @DisplayName("Should fail validation when trying to register user with no dot in email")
    void shouldFailValidationWhenTryingToRegisterUserWithNoDotInEmail() throws Exception {
        //given
        final UserRegistrationRequest request = UserRegistrationRequestSamples.withNoDotInEmail();

        //when

        //then
        mvc.perform(post(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email is not valid"));
    }

    @Test
    @DisplayName("Should fail validation when trying to register user with dot as last character in email")
    void shouldFailValidationWhenTryingToRegisterUserWithDotAsLastCharacterInEmail() throws Exception {
        //given
        final UserRegistrationRequest request = UserRegistrationRequestSamples.withDotAsLastCharacterInEmail();

        //when

        //then
        mvc.perform(post(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email is not valid"));
    }

    @Test
    @DisplayName("Should fail validation when trying to register user with null password")
    void shouldFailValidationWhenTryingToRegisterUserWithNullPassword() throws Exception {
        //given
        final UserRegistrationRequest request = UserRegistrationRequestSamples.withNullPassword();

        //when

        //then
        mvc.perform(post(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Password must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to register user with empty password")
    void shouldFailValidationWhenTryingToRegisterUserWithEmptyPassword() throws Exception {
        //given
        final UserRegistrationRequest request = UserRegistrationRequestSamples.withEmptyPassword();

        //when

        //then
        mvc.perform(post(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(containsString("Password must not be blank")));
    }

    @Test
    @DisplayName("Should fail validation when trying to register user with blank password")
    void shouldFailValidationWhenTryingToRegisterUserWithBlankPassword() throws Exception {
        //given
        final UserRegistrationRequest request = UserRegistrationRequestSamples.withBlankPassword();

        //when

        //then
        mvc.perform(post(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(containsString("Password must not be blank")));
    }

    @Test
    @DisplayName("Should fail validation when trying to register user with too short password")
    void shouldFailValidationWhenTryingToRegisterUserWithTooShortPassword() throws Exception {
        //given
        final UserRegistrationRequest request = UserRegistrationRequestSamples.withTooShortPassword();

        //when

        //then
        mvc.perform(post(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Password should be at least 12 characters long"));
    }

    @Test
    @DisplayName("Should fail validation when trying to register user with null password confirmation")
    void shouldFailValidationWhenTryingToRegisterUserWithNullPasswordConfirmation() throws Exception {
        //given
        final UserRegistrationRequest request = UserRegistrationRequestSamples.withNullPasswordConfirmation();

        //when

        //then
        mvc.perform(post(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Password confirmation must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to register user with empty password confirmation")
    void shouldFailValidationWhenTryingToRegisterUserWithEmptyPasswordConfirmation() throws Exception {
        //given
        final UserRegistrationRequest request = UserRegistrationRequestSamples.withEmptyPasswordConfirmation();

        //when

        //then
        mvc.perform(post(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Password confirmation must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to register user with blank password confirmation")
    void shouldFailValidationWhenTryingToRegisterUserWithBlankPasswordConfirmation() throws Exception {
        //given
        final UserRegistrationRequest request = UserRegistrationRequestSamples.withBlankPasswordConfirmation();

        //when

        //then
        mvc.perform(post(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Password confirmation must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to register user with different password and password confirmation")
    void shouldFailValidationWhenTryingToRegisterUserWithDifferentPasswordAndPasswordConfirmation() throws Exception {
        //given
        final UserRegistrationRequest request = UserRegistrationRequestSamples.withDifferentPasswordAndConfirmation();

        //when

        //then
        mvc.perform(post(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Passwords do not match"));
    }

    @Test
    @DisplayName("Should pass validation when trying to register user")
    void shouldPassValidationWhenTryingToRegisterUser() throws Exception {
        //given
        final UserRegistrationRequest request = UserRegistrationRequestSamples.valid();

        //when

        //then
        mvc.perform(post(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should fail validation when trying to send null user registration confirmation request")
    void shouldFailValidationWhenTryingToSendNullUserRegistrationConfirmationRequest() throws Exception {
        //given

        //when

        //then
        mvc.perform(patch(USERS_API_SUFFIX)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to confirm registration with null token")
    void shouldFailValidationWhenTryingToConfirmRegistrationWihNullToken() throws Exception {
        //given
        final UserRegistrationConfirmationRequest request = UserRegistrationConfirmationRequestSamples.withNullToken();

        //when

        //then
        mvc.perform(patch(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Token must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to confirm registration with empty token")
    void shouldFailValidationWhenTryingToConfirmRegistrationWihEmptyToken() throws Exception {
        //given
        final UserRegistrationConfirmationRequest request = UserRegistrationConfirmationRequestSamples.withEmptyToken();

        //when

        //then
        mvc.perform(patch(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Token must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to confirm registration with blank token")
    void shouldFailValidationWhenTryingToConfirmRegistrationWihBlankToken() throws Exception {
        //given
        final UserRegistrationConfirmationRequest request = UserRegistrationConfirmationRequestSamples.withBlankToken();

        //when

        //then
        mvc.perform(patch(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Token must not be blank"));
    }

    @Test
    @DisplayName("Should pass validation when trying to confirm registration")
    void shouldPassValidationWhenTryingToConfirmRegistration() throws Exception {
        //given
        final UserRegistrationConfirmationRequest request = UserRegistrationConfirmationRequestSamples.notBlank();

        //when

        //then
        mvc.perform(patch(USERS_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should fail validation when trying to send null user login request")
    void shouldFailValidationWhenTryingToSendNullUserLoginRequest() throws Exception {
        //given

        //when

        //then
        mvc.perform(post(USERS_LOGIN_API_SUFFIX)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to login with null email")
    void shouldFailValidationWhenTryingToLoginWithNullEmail() throws Exception {
        //given
        final UserLoginRequest request = UserLoginRequestSamples.withNullEmail();

        //when

        //then
        mvc.perform(post(USERS_LOGIN_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to login with empty email")
    void shouldFailValidationWhenTryingToLoginWithEmptyEmail() throws Exception {
        //given
        final UserLoginRequest request = UserLoginRequestSamples.withEmptyEmail();

        //when

        //then
        mvc.perform(post(USERS_LOGIN_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to login with blank email")
    void shouldFailValidationWhenTryingToLoginWithBlankEmail() throws Exception {
        //given
        final UserLoginRequest request = UserLoginRequestSamples.withBlankEmail();

        //when

        //then
        mvc.perform(post(USERS_LOGIN_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to login with no '@' sign in email")
    void shouldFailValidationWhenTryingToLoginWithNoAtSignInEmail() throws Exception {
        //given
        final UserLoginRequest request = UserLoginRequestSamples.withNoAtSignInEmail();

        //when

        //then
        mvc.perform(post(USERS_LOGIN_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email is not valid"));
    }

    @Test
    @DisplayName("Should fail validation when trying to login with two '@' signs in email")
    void shouldFailValidationWhenTryingToLoginWithTwoAtSignsInEmail() throws Exception {
        //given
        final UserLoginRequest request = UserLoginRequestSamples.withTwoAtSignsInEmail();

        //when

        //then
        mvc.perform(post(USERS_LOGIN_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email is not valid"));
    }

    @Test
    @DisplayName("Should fail validation when trying to login with no dot in email")
    void shouldFailValidationWhenTryingToLoginWithNoDotInEmail() throws Exception {
        //given
        final UserLoginRequest request = UserLoginRequestSamples.withNoDotInEmail();

        //when

        //then
        mvc.perform(post(USERS_LOGIN_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email is not valid"));
    }

    @Test
    @DisplayName("Should fail validation when trying to login with dot as last character in email")
    void shouldFailValidationWhenTryingToLoginWithDotAsLastCharacterInEmail() throws Exception {
        //given
        final UserLoginRequest request = UserLoginRequestSamples.withDotAsLastCharacterInEmail();

        //when

        //then
        mvc.perform(post(USERS_LOGIN_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email is not valid"));
    }

    @Test
    @DisplayName("Should fail validation when trying to login with null password")
    void shouldFailValidationWhenTryingToLoginWithNullPassword() throws Exception {
        //given
        final UserLoginRequest request = UserLoginRequestSamples.withNullPassword();

        //when

        //then
        mvc.perform(post(USERS_LOGIN_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Password must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to login with empty password")
    void shouldFailValidationWhenTryingToLoginWithEmptyPassword() throws Exception {
        //given
        final UserLoginRequest request = UserLoginRequestSamples.withEmptyPassword();

        //when

        //then
        mvc.perform(post(USERS_LOGIN_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Password must not be blank"));
    }

    @Test
    @DisplayName("Should fail validation when trying to login with blank password")
    void shouldFailValidationWhenTryingToLoginWithBlankPassword() throws Exception {
        //given
        final UserLoginRequest request = UserLoginRequestSamples.withBlankPassword();

        //when

        //then
        mvc.perform(post(USERS_LOGIN_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Password must not be blank"));
    }

    @Test
    @DisplayName("Should pass validation and return token when trying to login")
    void shouldPassValidationAndReturnTokenWhenTryingToLogin() throws Exception {
        //given
        final UserLoginRequest request = UserLoginRequestSamples.valid();

        //when

        //then
        mvc.perform(post(USERS_LOGIN_API_SUFFIX)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("token").value("mock-token"));
    }

    private static String asJson(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}