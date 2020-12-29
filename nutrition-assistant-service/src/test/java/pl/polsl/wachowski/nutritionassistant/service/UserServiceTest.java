package pl.polsl.wachowski.nutritionassistant.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.VerificationTokenEntity;
import pl.polsl.wachowski.nutritionassistant.domain.repository.TokenRepository;
import pl.polsl.wachowski.nutritionassistant.domain.repository.UserRepository;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenException;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenExpiredException;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenNotFoundException;
import pl.polsl.wachowski.nutritionassistant.exception.user.UserAlreadyActiveException;
import pl.polsl.wachowski.nutritionassistant.exception.user.UserExistsException;
import pl.polsl.wachowski.nutritionassistant.util.MockPasswordEncoder;
import pl.polsl.wachowski.nutritionassistant.samples.UserEntitySamples;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@ContextConfiguration(classes = {UserRepository.class, TokenRepository.class})
@EnableJpaRepositories(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.repository")
@EntityScan(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.db")
class UserServiceTest {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final UserService userService;

    @Autowired
    UserServiceTest(final UserRepository userRepository, final TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.userService = new UserService(userRepository,
                                           tokenRepository,
                                           new MockPasswordEncoder());
    }

    @Test
    @DisplayName("Should throw UserExistsException when trying to add user with the same email as existing inactive user")
    void shouldThrowUserExistsExceptionWhenTryingToAddUserWithTheSameEmailAsExistingInactiveUser() {
        //given
        final String userEmail = "foo@bar.com";
        final UserEntity user = UserEntitySamples.inactiveUser(userEmail);
        final Executable executable = () -> userService.addUser(userEmail, "password");

        //when
        userRepository.save(user);

        //then
        assertThrows(UserExistsException.class, executable);
    }

    @Test
    @DisplayName("Should throw UserExistsException when trying to add user with the same email as existing active user")
    void shouldThrowUserExistsExceptionWhenTryingToAddUserWithTheSameEmailAsExistingActiveUser() {
        //given
        final String userEmail = "foo@bar.com";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final Executable executable = () -> userService.addUser(userEmail, "password");

        //when
        userRepository.save(user);

        //then
        assertThrows(UserExistsException.class, executable);
    }

    @Test
    @DisplayName("Should add new user")
    void shouldAddNewUser() throws UserExistsException {
        //given
        final UserEntity existingUser = UserEntitySamples.activeUser("foo@bar.com");
        final String newUserEmail = "bar@foo.com";
        final String newUserPassword = "password";

        //when
        userRepository.save(existingUser);
        final UserEntity newUser = userService.addUser(newUserEmail, newUserPassword);

        //then
        assertEquals(newUser.getEmail(), newUserEmail);
    }

    @Test
    @DisplayName("Should throw VerificationTokenNotFoundException when trying to activate user with non-existing token")
    void shouldThrowVerificationTokenNotFoundExceptionWhenTryingToActivateUserWithNonExistingToken() {
        //given
        final UserEntity user = UserEntitySamples.inactiveUser("foo@bar.com");
        final VerificationTokenEntity token = new VerificationTokenEntity("token",
                                                                          LocalDateTime.now().plusDays(1),
                                                                          user);
        final Executable executable = () -> userService.activateUser("non-existing token");

        //when
        userRepository.save(user);
        tokenRepository.save(token);

        //then
        assertThrows(VerificationTokenNotFoundException.class, executable);
    }

    @Test
    @DisplayName("Should throw VerificationTokenExpiredException when trying to activate user with expired token")
    void shouldThrowVerificationTokenExpiredExceptionWhenTryingToActivateUserWithExpiredToken() {
        //given
        final UserEntity user = UserEntitySamples.inactiveUser("foo@bar.com");
        final String tokenValue = "token";
        final VerificationTokenEntity token = new VerificationTokenEntity(tokenValue,
                                                                          LocalDateTime.now(),
                                                                          user);
        final Executable executable = () -> userService.activateUser(tokenValue);

        //when
        userRepository.save(user);
        tokenRepository.save(token);

        //then
        assertThrows(VerificationTokenExpiredException.class, executable);
    }

    @Test
    @DisplayName("Should throw UserAlreadyActiveException when trying to activate an active user")
    void shouldThrowUserAlreadyActiveExceptionWhenTryingToActivateAnActiveUser() {
        //given
        final UserEntity user = UserEntitySamples.activeUser("foo@bar.com");
        final String tokenValue = "token";
        final VerificationTokenEntity token = new VerificationTokenEntity(tokenValue,
                                                                          LocalDateTime.now().plusDays(1),
                                                                          user);
        final Executable executable = () -> userService.activateUser(tokenValue);

        //when
        userRepository.save(user);
        tokenRepository.save(token);

        //then
        assertThrows(UserAlreadyActiveException.class, executable);
    }

    @Test
    @DisplayName("Should activate user")
    void shouldActivateUser() throws UserAlreadyActiveException, VerificationTokenException {
        //given
        final String userEmail = "foo@bar.com";
        final UserEntity inactiveUser = UserEntitySamples.inactiveUser(userEmail);
        final String tokenValue = "token";
        final VerificationTokenEntity token = new VerificationTokenEntity(tokenValue,
                                                                          LocalDateTime.now().plusDays(1),
                                                                          inactiveUser);

        //when
        userRepository.save(inactiveUser);
        tokenRepository.save(token);
        userService.activateUser(tokenValue);
        final UserEntity activeUser = userRepository.findUserByEmail(userEmail);

        //then
        assertTrue(activeUser.isActive());
    }

    @Test
    @DisplayName("Should create verification token")
    void shouldCreateVerificationToken() {
        //given
        final UserEntity user = UserEntitySamples.inactiveUser("foo@bar.com");
        final String tokenValue = "token";

        //when
        userRepository.save(user);
        userService.createVerificationToken(tokenValue, user);
        final VerificationTokenEntity existingToken = tokenRepository.findVerificationTokenByValue(tokenValue);

        //then
        assertEquals(user, existingToken.getUser());
    }

    @Test
    @DisplayName("Should return null when trying to get user by non-existing email")
    void shouldReturnNullWhenTryingToGetUserByNonExistingEmail() {
        //given
        final String userEmail = "foo@bar.com";

        //when
        final UserEntity user = userService.getUserByEmail(userEmail);

        //then
        assertNull(user);
    }

    @Test
    @DisplayName("Should return inactive user")
    void shouldReturnInactiveUser() {
        //given
        final String userEmail = "foo@bar.com";
        final UserEntity existingUser = UserEntitySamples.inactiveUser(userEmail);

        //when
        userRepository.save(existingUser);
        final UserEntity foundUser = userService.getUserByEmail(userEmail);

        //then
        assertEquals(existingUser, foundUser);
    }

    @Test
    @DisplayName("Should return active user")
    void shouldReturnActiveUser() {
        //given
        final String userEmail = "foo@bar.com";
        final UserEntity existingUser = UserEntitySamples.activeUser(userEmail);

        //when
        userRepository.save(existingUser);
        final UserEntity foundUser = userService.getUserByEmail(userEmail);

        //then
        assertEquals(existingUser, foundUser);
    }

}