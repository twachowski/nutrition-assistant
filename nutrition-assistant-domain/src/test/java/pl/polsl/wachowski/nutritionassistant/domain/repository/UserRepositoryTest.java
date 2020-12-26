package pl.polsl.wachowski.nutritionassistant.domain.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.util.UserEntitySamples;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {UserRepository.class})
@EnableJpaRepositories(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.repository")
@EntityScan(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.db")
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    UserRepositoryTest(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    @DisplayName("Should return UserEntity if inactive user with given email exists")
    void shouldReturnUserEntityIfInactiveUserWithGivenEmailExists() {
        //given
        final String userEmail = "foo@bar.com";
        final UserEntity userEntity = UserEntitySamples.inactiveUser(userEmail);

        //when
        userRepository.save(userEntity);
        final UserEntity foundUser = userRepository.findUserByEmail(userEmail);

        //then
        assertEquals(userEmail, foundUser.getEmail());
        assertFalse(foundUser.isActive());
    }

    @Test
    @DisplayName("Should return UserEntity if active user with given email exists")
    void shouldReturnUserEntityIfActiveUserWithGivenEmailExists() {
        //given
        final String userEmail = "foo@bar.com";
        final UserEntity userEntity = UserEntitySamples.activeUser(userEmail);

        //when
        userRepository.save(userEntity);
        final UserEntity foundUser = userRepository.findUserByEmail(userEmail);

        //then
        assertEquals(userEmail, foundUser.getEmail());
        assertTrue(foundUser.isActive());
    }

    @Test
    @DisplayName("Should return null if user with given email does not exist")
    void shouldReturnNullIfUserWithGivenEmailDoesNotExist() {
        //given
        final String existingUserEmail = "foo@bar.com";
        final UserEntity existingUser = UserEntitySamples.activeUser(existingUserEmail);
        final String missingUserEmail = "bar@foo.org";

        //when
        userRepository.save(existingUser);
        final UserEntity missingUser = userRepository.findUserByEmail(missingUserEmail);

        //then
        assertNull(missingUser);
    }

}