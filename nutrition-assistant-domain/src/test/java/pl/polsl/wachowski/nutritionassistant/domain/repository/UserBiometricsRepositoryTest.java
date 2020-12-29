package pl.polsl.wachowski.nutritionassistant.domain.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserBiometricsEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.samples.UserEntitySamples;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ContextConfiguration(classes = {UserRepository.class, UserBiometricsRepository.class})
@EnableJpaRepositories(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.repository")
@EntityScan(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.db")
class UserBiometricsRepositoryTest {

    private final UserRepository userRepository;
    private final UserBiometricsRepository userBiometricsRepository;

    @Autowired
    public UserBiometricsRepositoryTest(final UserRepository userRepository,
                                        final UserBiometricsRepository userBiometricsRepository) {
        this.userRepository = userRepository;
        this.userBiometricsRepository = userBiometricsRepository;
    }

    @Test
    @DisplayName("Should return non-empty Optional if inactive user with given email exists")
    void shouldReturnNonEmptyOptionalIfInactiveUserWithGivenEmailExists() {
        //given
        final String userEmail = "foo@bar.com";
        final UserEntity userEntity = UserEntitySamples.inactiveUser(userEmail);

        //when
        userRepository.save(userEntity);
        final Optional<UserBiometricsEntity> userBiometricsOptional = userBiometricsRepository.findUserBiometricsByUserEmail(userEmail);

        //then
        assertTrue(userBiometricsOptional.isPresent());
    }

    @Test
    @DisplayName("Should return non-empty Optional if active user with given email exists")
    void shouldReturnNonEmptyOptionalIfActiveUserWithGivenEmailExists() {
        //given
        final String userEmail = "foo@bar.com";
        final UserEntity userEntity = UserEntitySamples.activeUser(userEmail);

        //when
        userRepository.save(userEntity);
        final Optional<UserBiometricsEntity> userBiometricsOptional = userBiometricsRepository.findUserBiometricsByUserEmail(userEmail);

        //then
        assertTrue(userBiometricsOptional.isPresent());
    }

    @Test
    @DisplayName("Should return empty Optional if user with given email does not exist")
    void shouldReturnEmptyOptionalIfUserWithGivenEmailDoesNotExist() {
        //given
        final String existingUserEmail = "foo@bar.com";
        final UserEntity existingUser = UserEntitySamples.inactiveUser(existingUserEmail);
        final String missingUserEmail = "bar@foo.org";

        //when
        userRepository.save(existingUser);
        final Optional<UserBiometricsEntity> userBiometricsOptional = userBiometricsRepository.findUserBiometricsByUserEmail(missingUserEmail);

        //then
        assertFalse(userBiometricsOptional.isPresent());
    }

}