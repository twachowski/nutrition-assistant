package pl.polsl.wachowski.nutritionassistant.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import pl.polsl.wachowski.nutritionassistant.api.user.UserBiometrics;
import pl.polsl.wachowski.nutritionassistant.api.user.UserSimpleBiometrics;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserBiometricsEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.repository.UserBiometricsRepository;
import pl.polsl.wachowski.nutritionassistant.domain.repository.UserRepository;
import pl.polsl.wachowski.nutritionassistant.util.UserBiometricsEntitySamples;
import pl.polsl.wachowski.nutritionassistant.util.UserBiometricsSamples;
import pl.polsl.wachowski.nutritionassistant.util.UserEntitySamples;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@ContextConfiguration(classes = {UserRepository.class, UserBiometricsRepository.class})
@EnableJpaRepositories(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.repository")
@EntityScan(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.db")
class ProfileServiceTest {

    private final UserRepository userRepository;
    private final UserBiometricsRepository userBiometricsRepository;
    private AuthenticationService authenticationService;
    private ProfileService profileService;

    @Autowired
    ProfileServiceTest(final UserRepository userRepository, final UserBiometricsRepository userBiometricsRepository) {
        this.userRepository = userRepository;
        this.userBiometricsRepository = userBiometricsRepository;
    }

    @BeforeEach
    void setUp() {
        this.authenticationService = new MockAuthenticationService();
        this.profileService = new ProfileService(userBiometricsRepository, authenticationService);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when trying to get biometrics with no authenticated user")
    void shouldThrowIllegalStateExceptionWhenTryingToGetBiometricsWithNoAuthenticatedUser() {
        //given
        final Executable executable = profileService::getAuthenticatedUserBiometrics;

        //when

        //then
        final IllegalStateException exception = assertThrows(IllegalStateException.class, executable);
        assertTrue(exception.getMessage().startsWith("User"));
        assertTrue(exception.getMessage().endsWith("has not been found"));
    }

    @Test
    @DisplayName("Should throw IllegalStateException when trying to get non-existing user biometrics")
    void shouldThrowIllegalStateExceptionWhenTryingToGetNonExistingUserBiometrics() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final Executable executable = profileService::getAuthenticatedUserBiometrics;

        //when
        authenticationService.authenticate(userEmail, userPassword);

        //then
        final IllegalStateException exception = assertThrows(IllegalStateException.class, executable);
        assertTrue(exception.getMessage().startsWith("User"));
        assertTrue(exception.getMessage().endsWith("has not been found"));
    }

    @Test
    @DisplayName("Should return authenticated user biometrics")
    void shouldReturnAuthenticatedUserBiometrics() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final UserBiometrics userBiometrics = profileService.getAuthenticatedUserBiometrics();

        //then
        assertEquals(toUserBiometrics(user.getUserBiometrics()), userBiometrics);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when trying to get biometrics entity with no authenticated user")
    void shouldThrowIllegalStateExceptionWhenTryingToGetBiometricsEntityWithNoAuthenticatedUser() {
        //given
        final Executable executable = profileService::getAuthenticatedUserBiometricsEntity;

        //when

        //then
        final IllegalStateException exception = assertThrows(IllegalStateException.class, executable);
        assertTrue(exception.getMessage().startsWith("User"));
        assertTrue(exception.getMessage().endsWith("has not been found"));
    }

    @Test
    @DisplayName("Should throw IllegalStateException when trying to get non-existing user biometrics entity")
    void shouldThrowIllegalStateExceptionWhenTryingToGetNonExistingUserBiometricsEntity() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final Executable executable = profileService::getAuthenticatedUserBiometricsEntity;

        //when
        authenticationService.authenticate(userEmail, userPassword);

        //then
        final IllegalStateException exception = assertThrows(IllegalStateException.class, executable);
        assertTrue(exception.getMessage().startsWith("User"));
        assertTrue(exception.getMessage().endsWith("has not been found"));
    }

    @Test
    @DisplayName("Should return authenticated user biometrics entity")
    void shouldReturnAuthenticatedUserBiometricsEntity() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final UserBiometricsEntity userBiometrics = profileService.getAuthenticatedUserBiometricsEntity();

        //then
        assertEquals(user.getUserBiometrics(), userBiometrics);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when trying to update biometrics with no authenticated user")
    void shouldThrowIllegalStateExceptionWhenTryingToUpdateBiometricsWithNoAuthenticatedUser() {
        //given
        final UserBiometrics userBiometrics = UserBiometricsSamples.shortFemale();
        final Executable executable = () -> profileService.updateUserBiometrics(userBiometrics);

        //when

        //then
        final IllegalStateException exception = assertThrows(IllegalStateException.class, executable);
        assertTrue(exception.getMessage().startsWith("User"));
        assertTrue(exception.getMessage().endsWith("has not been found"));
    }

    @Test
    @DisplayName("Should throw IllegalStateException when trying to update non-existing user biometrics")
    void shouldThrowIllegalStateExceptionWhenTryingToUpdateNonExistingUserBiometrics() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserBiometrics userBiometrics = UserBiometricsSamples.shortFemale();
        final Executable executable = () -> profileService.updateUserBiometrics(userBiometrics);

        //when
        authenticationService.authenticate(userEmail, userPassword);

        //then
        final IllegalStateException exception = assertThrows(IllegalStateException.class, executable);
        assertTrue(exception.getMessage().startsWith("User"));
        assertTrue(exception.getMessage().endsWith("has not been found"));
    }

    @Test
    @DisplayName("Should update authenticated user biometrics")
    void shouldUpdateAuthenticatedUserBiometrics() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final UserBiometrics userBiometricsBeforeUpdate = toUserBiometrics(user.getUserBiometrics());
        final UserBiometrics newUserBiometrics = UserBiometricsSamples.shortFemale();

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        profileService.updateUserBiometrics(newUserBiometrics);
        final UserBiometrics userBiometricsAfterUpdate = profileService.getAuthenticatedUserBiometrics();

        //then
        assertNotEquals(newUserBiometrics, userBiometricsBeforeUpdate);
        assertEquals(newUserBiometrics, userBiometricsAfterUpdate);
    }

    @Test
    @DisplayName("Should return simple biometrics with appropriate user age")
    void shouldReturnSimpleBiometricsWithAppropriateUserAge() {
        //given
        final short userAge = 23;
        final UserBiometricsEntity userBiometrics = UserBiometricsEntitySamples.shortFemaleOfGivenAge(userAge);

        //when
        final UserSimpleBiometrics simpleBiometrics = ProfileService.toSimpleBiometrics(userBiometrics);

        //then
        assertEquals(userAge, simpleBiometrics.getAge());
        assertEquals(userBiometrics.getSex(), simpleBiometrics.getSex());
        assertEquals(userBiometrics.getHeight(), simpleBiometrics.getHeight());
        assertEquals(userBiometrics.getWeight(), simpleBiometrics.getWeight());
    }

    private static UserBiometrics toUserBiometrics(final UserBiometricsEntity userBiometricsEntity) {
        return new UserBiometrics(userBiometricsEntity.getDateOfBirth(),
                                  userBiometricsEntity.getSex(),
                                  userBiometricsEntity.getHeight(),
                                  userBiometricsEntity.getWeight(),
                                  userBiometricsEntity.getActivityLevel(),
                                  userBiometricsEntity.getCalorieGoal());
    }

}