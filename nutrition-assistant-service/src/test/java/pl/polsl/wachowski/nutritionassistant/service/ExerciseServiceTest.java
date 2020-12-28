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
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise.EditedExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.api.exercise.Exercise;
import pl.polsl.wachowski.nutritionassistant.api.exercise.ExerciseSearchResponse;
import pl.polsl.wachowski.nutritionassistant.api.units.TimeUnit;
import pl.polsl.wachowski.nutritionassistant.client.MockNutritionixClient;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.DiaryEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.ExerciseEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.repository.DiaryRepository;
import pl.polsl.wachowski.nutritionassistant.domain.repository.ExerciseRepository;
import pl.polsl.wachowski.nutritionassistant.domain.repository.UserBiometricsRepository;
import pl.polsl.wachowski.nutritionassistant.domain.repository.UserRepository;
import pl.polsl.wachowski.nutritionassistant.exception.entry.EntryNotFoundException;
import pl.polsl.wachowski.nutritionassistant.provider.exercise.ExerciseProvider;
import pl.polsl.wachowski.nutritionassistant.provider.exercise.NutritionixExerciseProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.util.UserEntitySamples;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@ContextConfiguration(classes = {
        UserRepository.class,
        DiaryRepository.class,
        UserBiometricsRepository.class,
        ExerciseRepository.class
})
@EnableJpaRepositories(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.repository")
@EntityScan(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.db")
class ExerciseServiceTest {

    private final UserRepository userRepository;
    private final UserBiometricsRepository userBiometricsRepository;
    private final DiaryRepository diaryRepository;
    private final ExerciseRepository exerciseRepository;
    private ExerciseService exerciseService;
    private AuthenticationService authenticationService;

    @Autowired
    ExerciseServiceTest(final UserRepository userRepository,
                        final DiaryRepository diaryRepository,
                        final UserBiometricsRepository userBiometricsRepository,
                        final ExerciseRepository exerciseRepository) {
        this.userRepository = userRepository;
        this.userBiometricsRepository = userBiometricsRepository;
        this.diaryRepository = diaryRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @BeforeEach
    void setUp() {
        this.authenticationService = new MockAuthenticationService();
        final ProfileService profileService = new ProfileService(userBiometricsRepository, authenticationService);
        final ExerciseProvider exerciseProvider = new NutritionixExerciseProviderAdapter(new MockNutritionixClient());
        this.exerciseService = new ExerciseService(exerciseRepository,
                                                   exerciseProvider,
                                                   profileService);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when trying to search exercises with no authenticated user")
    void shouldThrowIllegalStateExceptionWhenTryingToSearchExercisesWithNoAuthenticatedUser() {
        //given
        final Executable executable = () -> exerciseService.searchExercises("running");

        //when

        //then
        assertThrows(IllegalStateException.class, executable);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when trying to search exercises with non-existing user authenticated")
    void shouldThrowIllegalStateExceptionWhenTryingToSearchExercisesWithNonExistingUserAuthenticated() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final Executable executable = () -> exerciseService.searchExercises("running");

        //when
        authenticationService.authenticate(userEmail, userPassword);

        //then
        assertThrows(IllegalStateException.class, executable);
    }

    @Test
    @DisplayName("Should return set of exercises")
    void shouldReturnSetOfExercises() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final String query = "running";

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final Set<Exercise> exercises = exerciseService.searchExercises(query);

        //then
        assertNotNull(exercises);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when trying to search exercises and user biometrics with no authenticated user")
    void shouldThrowIllegalStateExceptionWhenTryingToSearchExercisesAndUserBiometricsWithNoAuthenticatedUser() {
        //given
        final Executable executable = () -> exerciseService.searchExercisesWithBiometrics("running");

        //when

        //then
        assertThrows(IllegalStateException.class, executable);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when trying to search exercises and non-existing user biometrics")
    void shouldThrowIllegalStateExceptionWhenTryingToSearchExercisesAndNonExistingUserBiometrics() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final Executable executable = () -> exerciseService.searchExercisesWithBiometrics("running");

        //when
        authenticationService.authenticate(userEmail, userPassword);

        //then
        assertThrows(IllegalStateException.class, executable);
    }

    @Test
    @DisplayName("Should return set of exercises with authenticated user biometrics")
    void shouldReturnSetOfExercisesWithAuthenticatedUserBiometrics() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final String query = "running";

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final ExerciseSearchResponse response = exerciseService.searchExercisesWithBiometrics(query);

        //then
        assertNotNull(response.getExercises());
        assertNotNull(response.getUserSimpleBiometrics());
    }

    @Test
    @DisplayName("Should throw EntryNotFoundException when no exercise entry has given position")
    void shouldThrowEntryNotFoundExceptionWhenNoExerciseEntryHasGivenPosition() {
        //given
        final List<ExerciseEntryEntity> exerciseEntries = Collections.emptyList();
        final short entryPosition = 0;
        final EditedExerciseEntry editedExerciseEntry = new EditedExerciseEntry(TimeUnit.HOUR, BigDecimal.ONE);
        final Executable executable = () -> exerciseService.editExerciseEntry(exerciseEntries,
                                                                              entryPosition,
                                                                              editedExerciseEntry);

        //when

        //then
        assertThrows(EntryNotFoundException.class, executable);
    }

    @Test
    @DisplayName("Should edit exercise entry")
    void shouldEditExerciseEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final DiaryEntryEntity diaryEntry = new DiaryEntryEntity(diaryDate, user);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        final short exerciseEntryPosition = 0;
        final ExerciseEntryEntity oldExerciseEntry = new ExerciseEntryEntity("running",
                                                                             TimeUnit.MINUTE,
                                                                             BigDecimal.TEN,
                                                                             (short) 0,
                                                                             diaryEntry);
        diaryEntry.add(oldExerciseEntry);
        final EditedExerciseEntry editedExerciseEntry = new EditedExerciseEntry(TimeUnit.HOUR, BigDecimal.ONE);

        //when
        userRepository.save(user);
        final TimeUnit oldExerciseEntryTimeUnit = oldExerciseEntry.getTimeUnit();
        final BigDecimal oldExerciseEntryDuration = oldExerciseEntry.getAmount();
        exerciseService.editExerciseEntry(diaryEntry.getExerciseEntries(),
                                          exerciseEntryPosition,
                                          editedExerciseEntry);
        final ExerciseEntryEntity newExerciseEntry = diaryRepository.findDiaryEntryFetchExerciseEntries(userEmail, diaryDate)
                .getExerciseEntries()
                .iterator()
                .next();

        //then
        assertNotEquals(editedExerciseEntry.getTimeUnit(), oldExerciseEntryTimeUnit);
        assertNotEquals(editedExerciseEntry.getDuration(), oldExerciseEntryDuration);
        assertEquals(editedExerciseEntry.getTimeUnit(), newExerciseEntry.getTimeUnit());
        assertEquals(editedExerciseEntry.getDuration(), newExerciseEntry.getAmount());
    }

}