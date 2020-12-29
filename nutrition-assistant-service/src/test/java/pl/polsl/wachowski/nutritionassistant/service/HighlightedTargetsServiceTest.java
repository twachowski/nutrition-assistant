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
import pl.polsl.wachowski.nutritionassistant.api.targets.HighlightedTargetsRequest;
import pl.polsl.wachowski.nutritionassistant.api.targets.HighlightedTargetsResponse;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.targets.HighlightedTargetsEntity;
import pl.polsl.wachowski.nutritionassistant.domain.repository.HighlightedTargetsRepository;
import pl.polsl.wachowski.nutritionassistant.domain.repository.TokenRepository;
import pl.polsl.wachowski.nutritionassistant.domain.repository.UserRepository;
import pl.polsl.wachowski.nutritionassistant.exception.target.UnknownTargetNutrientException;
import pl.polsl.wachowski.nutritionassistant.samples.HighlightedTargetsRequestSamples;
import pl.polsl.wachowski.nutritionassistant.samples.UserEntitySamples;
import pl.polsl.wachowski.nutritionassistant.util.MockPasswordEncoder;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@ContextConfiguration(classes = {UserRepository.class, TokenRepository.class, HighlightedTargetsRepository.class})
@EnableJpaRepositories(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.repository")
@EntityScan(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.db")
class HighlightedTargetsServiceTest {

    private final EntityManager entityManager;
    private final UserService userService;
    private final UserRepository userRepository;
    private final HighlightedTargetsRepository highlightedTargetsRepository;

    private AuthenticationService authenticationService;
    private HighlightedTargetsService highlightedTargetsService;

    @Autowired
    HighlightedTargetsServiceTest(final EntityManager entityManager,
                                  final UserRepository userRepository,
                                  final TokenRepository tokenRepository,
                                  final HighlightedTargetsRepository highlightedTargetsRepository) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.userService = new UserService(userRepository, tokenRepository, new MockPasswordEncoder());
        this.highlightedTargetsRepository = highlightedTargetsRepository;
    }

    @BeforeEach
    void setUp() {
        this.authenticationService = new MockAuthenticationService();
        this.highlightedTargetsService = new HighlightedTargetsService(authenticationService,
                                                                       userService,
                                                                       highlightedTargetsRepository);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when trying to get highlighted targets with no authenticated user")
    void shouldThrowIllegalStateExceptionWhenTryingToGetHighlightedTargetsWithNoAuthenticatedUser() {
        //given
        final Executable executable = highlightedTargetsService::getAuthenticatedUserHighlightedTargets;

        //when

        //then
        assertThrows(IllegalStateException.class, executable);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when trying to get highlighted targets of non-existing authenticated user")
    void shouldThrowIllegalStateExceptionWhenTryingToGetHighlightedTargetsOfNonExistingAuthenticatedUser() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final Executable executable = highlightedTargetsService::getAuthenticatedUserHighlightedTargets;

        //when
        authenticationService.authenticate(userEmail, userPassword);

        //then
        assertThrows(IllegalStateException.class, executable);
    }

    @Test
    @DisplayName("Should return highlighted targets when user has no defined targets")
    void shouldReturnHighlightedTargetsWhenUserHasNoDefinedTargets() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final HighlightedTargetsResponse targets = highlightedTargetsService.getAuthenticatedUserHighlightedTargets();

        //then
        assertNotNull(targets.getTarget1());
        assertNotNull(targets.getTarget2());
        assertNotNull(targets.getTarget3());
        assertNotNull(targets.getTarget4());
        assertNotNull(targets.getTarget5());
        assertNotNull(targets.getTarget6());
    }

    @Test
    @DisplayName("Should return user highlighted targets")
    void shouldReturnUserHighlightedTargets() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final HighlightedTargetsEntity targets = HighlightedTargetsEntity.getDefault(user);
        user.setHighlightedTargets(targets);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final HighlightedTargetsResponse userTargets = highlightedTargetsService.getAuthenticatedUserHighlightedTargets();

        //then
        assertNotNull(userTargets.getTarget1());
        assertNotNull(userTargets.getTarget2());
        assertNotNull(userTargets.getTarget3());
        assertNotNull(userTargets.getTarget4());
        assertNotNull(userTargets.getTarget5());
        assertNotNull(userTargets.getTarget6());
    }

    @Test
    @DisplayName("Should throw IllegalStateException when trying to update highlighted targets with no authenticated user")
    void shouldThrowIllegalStateExceptionWhenTryingToUpdateHighlightedTargetsWithNoAuthenticatedUser() {
        //given
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.request();
        final Executable executable = () -> highlightedTargetsService.updateUserHighlightedTargets(request);

        //when

        //then
        assertThrows(IllegalStateException.class, executable);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when trying to update highlighted targets of non-existing authenticated user")
    void shouldThrowIllegalStateExceptionWhenTryingToUpdateHighlightedTargetsOfNonExistingAuthenticatedUser() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.request();
        final Executable executable = () -> highlightedTargetsService.updateUserHighlightedTargets(request);

        //when
        authenticationService.authenticate(userEmail, userPassword);

        //then
        assertThrows(IllegalStateException.class, executable);
    }

    @Test
    @DisplayName("Should throw UnknownTargetNutrientException when trying to update highlighted targets with null nutrient")
    void shouldThrowUnknownTargetNutrientExceptionWhenTryingToUpdateHighlightedTargetsWithNullNutrient() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withNullNutrient();
        final Executable executable = () -> highlightedTargetsService.updateUserHighlightedTargets(request);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);

        //then
        assertThrows(UnknownTargetNutrientException.class, executable);
    }

    @Test
    @DisplayName("Should throw UnknownTargetNutrientException when trying to update highlighted targets with non-existing nutrient")
    void shouldThrowUnknownTargetNutrientExceptionWhenTryingToUpdateHighlightedTargetsWithNonExistingNutrient() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.withNonExistingNutrient();
        final Executable executable = () -> highlightedTargetsService.updateUserHighlightedTargets(request);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);

        //then
        assertThrows(UnknownTargetNutrientException.class, executable);
    }

    @Test
    @DisplayName("Should create highlighted targets when trying to update highlighted targets for user with no defined targets")
    void shouldCreateHighlightedTargetsWhenTryingToUpdateHighlightedTargetsForUserWithNoDefinedTargets() throws UnknownTargetNutrientException {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.request();

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        highlightedTargetsService.updateUserHighlightedTargets(request);
        final HighlightedTargetsEntity targets = highlightedTargetsRepository.findHighlightedTargetsByUserEmail(userEmail)
                .orElseThrow(IllegalStateException::new);

        //then
        assertNotNull(targets.getNutrient1());
        assertNotNull(targets.getNutrient2());
        assertNotNull(targets.getNutrient3());
        assertNotNull(targets.getNutrient4());
        assertNotNull(targets.getNutrient5());
        assertNotNull(targets.getNutrient6());
    }

    @Test
    @DisplayName("Should update user highlighted targets")
    void shouldUpdateUserHighlightedTargets() throws UnknownTargetNutrientException {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final HighlightedTargetsEntity targetsBeforeUpdate = HighlightedTargetsEntity.getDefault(user);
        user.setHighlightedTargets(targetsBeforeUpdate);
        final HighlightedTargetsRequest request = HighlightedTargetsRequestSamples.request();

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        entityManager.clear();
        highlightedTargetsService.updateUserHighlightedTargets(request);
        final HighlightedTargetsEntity targetsAfterUpdate = highlightedTargetsRepository.findHighlightedTargetsByUserEmail(userEmail)
                .orElseThrow(IllegalStateException::new);

        //then
        assertNotEquals(targetsBeforeUpdate.getNutrient1(), targetsAfterUpdate.getNutrient1());
        assertNotEquals(targetsBeforeUpdate.getNutrient2(), targetsAfterUpdate.getNutrient2());
        assertNotEquals(targetsBeforeUpdate.getNutrient3(), targetsAfterUpdate.getNutrient3());
        assertNotEquals(targetsBeforeUpdate.getNutrient4(), targetsAfterUpdate.getNutrient4());
        assertNotEquals(targetsBeforeUpdate.getNutrient5(), targetsAfterUpdate.getNutrient5());
        assertNotEquals(targetsBeforeUpdate.getNutrient6(), targetsAfterUpdate.getNutrient6());
    }

}