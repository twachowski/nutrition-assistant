package pl.polsl.wachowski.nutritionassistant.domain.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.targets.HighlightedTargetsEntity;
import pl.polsl.wachowski.nutritionassistant.domain.samples.UserEntitySamples;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {UserRepository.class, HighlightedTargetsRepository.class})
@EnableJpaRepositories(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.repository")
@EntityScan(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.db")
class HighlightedTargetsRepositoryTest {

    private final UserRepository userRepository;
    private final HighlightedTargetsRepository highlightedTargetsRepository;

    @Autowired
    HighlightedTargetsRepositoryTest(final UserRepository userRepository,
                                     final HighlightedTargetsRepository highlightedTargetsRepository) {
        this.userRepository = userRepository;
        this.highlightedTargetsRepository = highlightedTargetsRepository;
    }

    @Test
    @DisplayName("Should return empty Optional when user with given email does not exist")
    void shouldReturnEmptyOptionalWhenUserWithGivenEmailDoesNotExist() {
        //given
        final String userEmail = "foo@bar.com";

        //when
        final Optional<HighlightedTargetsEntity> targetsOptional = highlightedTargetsRepository.findHighlightedTargetsByUserEmail(userEmail);

        //then
        assertFalse(targetsOptional.isPresent());
    }

    @Test
    @DisplayName("Should return empty Optional when inactive user with given email has no highlighted targets")
    void shouldReturnEmptyOptionalWhenInactiveUserWithGivenEmailHasNoHighlightedTargets() {
        //given
        final String userEmail = "foo@bar.com";
        final UserEntity userEntity = UserEntitySamples.inactiveUser(userEmail);

        //when
        userRepository.save(userEntity);
        final Optional<HighlightedTargetsEntity> targetsOptional = highlightedTargetsRepository.findHighlightedTargetsByUserEmail(userEmail);

        //then
        assertFalse(targetsOptional.isPresent());
    }

    @Test
    @DisplayName("Should return non-empty Optional when inactive user with given email has highlighted targets")
    void shouldReturnNonEmptyOptionalWhenInactiveUserWithGivenEmailHasHighlightedTargets() {
        //given
        final String userEmail = "foo@bar.com";
        final UserEntity userEntity = UserEntitySamples.inactiveUser(userEmail);
        final HighlightedTargetsEntity targets = HighlightedTargetsEntity.getDefault(userEntity);
        userEntity.setHighlightedTargets(targets);

        //when
        userRepository.save(userEntity);
        final Optional<HighlightedTargetsEntity> targetsOptional = highlightedTargetsRepository.findHighlightedTargetsByUserEmail(userEmail);

        //then
        assertTrue(targetsOptional.isPresent());
    }

    @Test
    @DisplayName("Should return empty Optional when active user with given email has no highlighted targets")
    void shouldReturnEmptyOptionalWhenActiveUserWithGivenEmailHasNoHighlightedTargets() {
        //given
        final String userEmail = "foo@bar.com";
        final UserEntity userEntity = UserEntitySamples.activeUser(userEmail);

        //when
        userRepository.save(userEntity);
        final Optional<HighlightedTargetsEntity> targetsOptional = highlightedTargetsRepository.findHighlightedTargetsByUserEmail(userEmail);

        //then
        assertFalse(targetsOptional.isPresent());
    }

    @Test
    @DisplayName("Should return non-empty Optional when active user with given email has highlighted targets")
    void shouldReturnNonEmptyOptionalWhenActiveUserWithGivenEmailHasHighlightedTargets() {
        //given
        final String userEmail = "foo@bar.com";
        final UserEntity userEntity = UserEntitySamples.activeUser(userEmail);
        final HighlightedTargetsEntity targets = HighlightedTargetsEntity.getDefault(userEntity);
        userEntity.setHighlightedTargets(targets);

        //when
        userRepository.save(userEntity);
        final Optional<HighlightedTargetsEntity> targetsOptional = highlightedTargetsRepository.findHighlightedTargetsByUserEmail(userEmail);

        //then
        assertTrue(targetsOptional.isPresent());
    }

}