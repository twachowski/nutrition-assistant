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
import pl.polsl.wachowski.nutritionassistant.api.diary.DiaryEntriesResponse;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise.EditedExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise.ExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.EditedFoodEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.FoodEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.note.NoteEntry;
import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.api.units.FoodMassUnit;
import pl.polsl.wachowski.nutritionassistant.api.units.TimeUnit;
import pl.polsl.wachowski.nutritionassistant.client.MockFdcClient;
import pl.polsl.wachowski.nutritionassistant.client.MockNutritionixClient;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.*;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.repository.*;
import pl.polsl.wachowski.nutritionassistant.exception.entry.EntryNotFoundException;
import pl.polsl.wachowski.nutritionassistant.facade.EntryFacade;
import pl.polsl.wachowski.nutritionassistant.facade.FoodFacade;
import pl.polsl.wachowski.nutritionassistant.provider.exercise.NutritionixExerciseProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.util.*;
import pl.polsl.wachowski.nutritionix.client.NutritionixClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@ContextConfiguration(classes = {
        UserRepository.class,
        UserBiometricsRepository.class,
        TokenRepository.class,
        DiaryRepository.class,
        FoodRepository.class,
        ExerciseRepository.class,
        NoteRepository.class
})
@EnableJpaRepositories(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.repository")
@EntityScan(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.db")
class DiaryServiceTest {

    private final UserService userService;
    private final NoteService noteService;
    private DiaryService diaryService;
    private AuthenticationService authenticationService;

    private final UserRepository userRepository;
    private final UserBiometricsRepository userBiometricsRepository;
    private final DiaryRepository diaryRepository;
    private final FoodRepository foodRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    DiaryServiceTest(final UserRepository userRepository,
                     final UserBiometricsRepository userBiometricsRepository,
                     final TokenRepository tokenRepository,
                     final DiaryRepository diaryRepository,
                     final FoodRepository foodRepository,
                     final ExerciseRepository exerciseRepository,
                     final NoteRepository noteRepository) {
        this.userService = new UserService(userRepository,
                                           tokenRepository,
                                           new MockPasswordEncoder());
        this.noteService = new NoteService(noteRepository);
        this.userRepository = userRepository;
        this.userBiometricsRepository = userBiometricsRepository;
        this.diaryRepository = diaryRepository;
        this.foodRepository = foodRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @BeforeEach
    void setUp() {
        this.authenticationService = new MockAuthenticationService();
        final ProfileService profileService = new ProfileService(userBiometricsRepository,
                                                                 authenticationService);
        final NutritionixClient nutritionixClient = new MockNutritionixClient();
        final FoodService foodService = FoodServiceSamples.foodService(foodRepository,
                                                                       new MockFdcClient(),
                                                                       nutritionixClient);
        final ExerciseService exerciseService = new ExerciseService(exerciseRepository,
                                                                    new NutritionixExerciseProviderAdapter(nutritionixClient),
                                                                    profileService);
        final EntryFacade entryFacade = new EntryFacade(new FoodFacade(foodService),
                                                        exerciseService,
                                                        noteService);
        this.diaryService = new DiaryService(authenticationService,
                                             userService,
                                             entryFacade,
                                             diaryRepository);
    }

    @Test
    @DisplayName("Should return empty diary instance when authenticated user has no diary entry for given diary date")
    void shouldReturnEmptyDiaryInstanceWhenAuthenticatedUserHasNoDiaryEntryForGivenDiaryDate() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final DiaryEntriesResponse response = diaryService.getDiaryEntries(diaryDate);

        //then
        assertSame(DiaryEntriesResponse.EMPTY_INSTANCE, response);
    }

    @Test
    @DisplayName("Should return diary response with empty entries when authenticated user has no sub-entries for given diary date")
    void shouldReturnDiaryResponseWithEmptyEntriesWhenAuthenticatedUserHasNoSubEntriesForGivenDiaryDate() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final DiaryEntryEntity diaryEntry = new DiaryEntryEntity(diaryDate, user);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final DiaryEntriesResponse response = diaryService.getDiaryEntries(diaryDate);

        //then
        assertNotSame(DiaryEntriesResponse.EMPTY_INSTANCE, response);
        assertTrue(response.getFoodEntries().isEmpty());
        assertTrue(response.getExerciseEntries().isEmpty());
        assertTrue(response.getNoteEntries().isEmpty());
    }

    @Test
    @DisplayName("Should return diary response with non-empty sub-entries")
    void shouldReturnDiaryResponseWithNonEmptySubEntries() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final DiaryEntryEntity diaryEntry = new DiaryEntryEntity(diaryDate, user);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        diaryEntry.add(new FoodEntryEntity("12345",
                                           NutritionDataProvider.USDA,
                                           FoodMassUnit.GRAM,
                                           BigDecimal.ONE,
                                           (short) 0,
                                           diaryEntry));
        diaryEntry.add(new ExerciseEntryEntity("running",
                                               TimeUnit.HOUR,
                                               BigDecimal.ONE,
                                               (short) 1,
                                               diaryEntry));
        diaryEntry.add(new NoteEntryEntity("note",
                                           (short) 2,
                                           diaryEntry));

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final DiaryEntriesResponse response = diaryService.getDiaryEntries(diaryDate);

        //then
        assertFalse(response.getFoodEntries().isEmpty());
        assertFalse(response.getExerciseEntries().isEmpty());
        assertFalse(response.getNoteEntries().isEmpty());
    }

    @Test
    @DisplayName("Should create diary entry with new food entry when user has no diary entry for given date")
    void shouldCreateDiaryEntryWithNewFoodEntryWhenUserHasNoDiaryEntryForGivenDate() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final FoodEntry newFoodEntry = FoodEntrySamples.foodEntry();

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final DiaryEntryEntity diaryEntryBeforeChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate);
        diaryService.addFoodEntry(diaryDate, newFoodEntry);
        final DiaryEntryEntity diaryEntryAfterChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate);

        //then
        assertNull(diaryEntryBeforeChange);
        assertEquals(1, diaryEntryAfterChange.getFoodEntries().size());
        assertTrue(diaryEntryAfterChange.getExerciseEntries().isEmpty());
        assertTrue(diaryEntryAfterChange.getNoteEntries().isEmpty());
    }

    @Test
    @DisplayName("Should add food entry to existing diary entry")
    void shouldAddFoodEntryToExistingDiaryEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final DiaryEntryEntity diaryEntry = new DiaryEntryEntity(diaryDate, user);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        diaryEntry.add(new FoodEntryEntity("12345",
                                           NutritionDataProvider.USDA,
                                           FoodMassUnit.GRAM,
                                           BigDecimal.TEN,
                                           (short) 0,
                                           diaryEntry));
        final FoodEntry newFoodEntry = FoodEntrySamples.foodEntry();

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final int foodEntriesCountBeforeChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate)
                .getFoodEntries()
                .size();
        diaryService.addFoodEntry(diaryDate, newFoodEntry);
        final int foodEntriesCountAfterChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate)
                .getFoodEntries()
                .size();

        //then
        assertEquals(foodEntriesCountBeforeChange + 1, foodEntriesCountAfterChange);
    }

    @Test
    @DisplayName("Should create diary entry with new exercise entry when user has no diary entry for given date")
    void shouldCreateDiaryEntryWithNewExerciseEntryWhenUserHasNoDiaryEntryForGivenDate() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final ExerciseEntry newExerciseEntry = ExerciseEntrySamples.exerciseEntry();

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final DiaryEntryEntity diaryEntryBeforeChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate);
        diaryService.addExerciseEntry(diaryDate, newExerciseEntry);
        final DiaryEntryEntity diaryEntryAfterChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate);

        //then
        assertNull(diaryEntryBeforeChange);
        assertEquals(1, diaryEntryAfterChange.getExerciseEntries().size());
        assertTrue(diaryEntryAfterChange.getFoodEntries().isEmpty());
        assertTrue(diaryEntryAfterChange.getNoteEntries().isEmpty());
    }

    @Test
    @DisplayName("Should add exercise entry to existing diary entry")
    void shouldAddExerciseEntryToExistingDiaryEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final DiaryEntryEntity diaryEntry = new DiaryEntryEntity(diaryDate, user);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        diaryEntry.add(new ExerciseEntryEntity("running",
                                               TimeUnit.MINUTE,
                                               BigDecimal.TEN,
                                               (short) 0,
                                               diaryEntry));
        final ExerciseEntry newExerciseEntry = ExerciseEntrySamples.exerciseEntry();

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final int exerciseEntriesCountBeforeChange = diaryRepository.findDiaryEntryFetchExerciseEntries(userEmail, diaryDate)
                .getExerciseEntries()
                .size();
        diaryService.addExerciseEntry(diaryDate, newExerciseEntry);
        final int exerciseEntriesCountAfterChange = diaryRepository.findDiaryEntryFetchExerciseEntries(userEmail, diaryDate)
                .getExerciseEntries()
                .size();

        //then
        assertEquals(exerciseEntriesCountBeforeChange + 1, exerciseEntriesCountAfterChange);
    }

    @Test
    @DisplayName("Should create diary entry with new note entry when user has no diary entry for given date")
    void shouldCreateDiaryEntryWithNewNoteEntryWhenUserHasNoDiaryEntryForGivenDate() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final NoteEntry newNoteEntry = new NoteEntry("note");

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final DiaryEntryEntity diaryEntryBeforeChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate);
        diaryService.addNoteEntry(diaryDate, newNoteEntry);
        final DiaryEntryEntity diaryEntryAfterChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate);

        //then
        assertNull(diaryEntryBeforeChange);
        assertEquals(1, diaryEntryAfterChange.getNoteEntries().size());
        assertTrue(diaryEntryAfterChange.getFoodEntries().isEmpty());
        assertTrue(diaryEntryAfterChange.getExerciseEntries().isEmpty());
    }

    @Test
    @DisplayName("Should add note entry to existing diary entry")
    void shouldAddNoteEntryToExistingDiaryEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final DiaryEntryEntity diaryEntry = new DiaryEntryEntity(diaryDate, user);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        diaryEntry.add(new NoteEntryEntity("note",
                                           (short) 0,
                                           diaryEntry));
        final NoteEntry newNoteEntry = new NoteEntry("new note");

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final int noteEntriesCountBeforeChange = diaryRepository.findDiaryEntryFetchNoteEntries(userEmail, diaryDate)
                .getNoteEntries()
                .size();
        diaryService.addNoteEntry(diaryDate, newNoteEntry);
        final int noteEntriesCountAfterChange = diaryRepository.findDiaryEntryFetchNoteEntries(userEmail, diaryDate)
                .getNoteEntries()
                .size();

        //then
        assertEquals(noteEntriesCountBeforeChange + 1, noteEntriesCountAfterChange);
    }

    @Test
    @DisplayName("Should throw EntryNotFoundException when trying to edit food entry in non-existing diary entry")
    void shouldThrowEntryNotFoundExceptionWhenTryingToEditFoodEntryInNonExistingDiaryEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final LocalDate diaryDate = LocalDate.now();
        final short entryPosition = 0;
        final EditedFoodEntry editedFoodEntry = new EditedFoodEntry(FoodMassUnit.OUNCE, BigDecimal.ONE);
        final Executable executable = () -> diaryService.editFoodEntry(diaryDate, entryPosition, editedFoodEntry);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);

        //then
        final EntryNotFoundException exception = assertThrows(EntryNotFoundException.class, executable);
        assertTrue(exception.getMessage().startsWith("Diary is empty"));
    }

    @Test
    @DisplayName("Should throw EntryNotFoundException when trying to edit food entry in empty diary entry")
    void shouldThrowEntryNotFoundExceptionWhenTryingToEditFoodEntryInEmptyDiaryEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final DiaryEntryEntity diaryEntry = new DiaryEntryEntity(diaryDate, user);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        final short entryPosition = 0;
        final EditedFoodEntry editedFoodEntry = new EditedFoodEntry(FoodMassUnit.OUNCE, BigDecimal.ONE);
        final Executable executable = () -> diaryService.editFoodEntry(diaryDate, entryPosition, editedFoodEntry);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);

        //then
        assertThrows(EntryNotFoundException.class, executable);
    }

    @Test
    @DisplayName("Should edit food entry")
    void shouldEditFoodEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final DiaryEntryEntity diaryEntry = new DiaryEntryEntity(diaryDate, user);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        final short entryPosition = 0;
        final FoodEntryEntity oldFoodEntry = new FoodEntryEntity("12345",
                                                                 NutritionDataProvider.USDA,
                                                                 FoodMassUnit.GRAM,
                                                                 BigDecimal.TEN,
                                                                 entryPosition,
                                                                 diaryEntry);
        diaryEntry.add(oldFoodEntry);
        final EditedFoodEntry editedFoodEntry = new EditedFoodEntry(FoodMassUnit.OUNCE,
                                                                    BigDecimal.ONE);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final FoodMassUnit oldFoodEntryMassUnit = oldFoodEntry.getUnit();
        final BigDecimal oldFoodEntryAmount = oldFoodEntry.getAmount();
        diaryService.editFoodEntry(diaryDate, entryPosition, editedFoodEntry);
        final FoodEntryEntity newFoodEntry = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate)
                .getFoodEntries()
                .iterator()
                .next();

        //then
        assertNotEquals(editedFoodEntry.getMassUnit(), oldFoodEntryMassUnit);
        assertNotEquals(editedFoodEntry.getAmount(), oldFoodEntryAmount);
        assertEquals(editedFoodEntry.getMassUnit(), newFoodEntry.getUnit());
        assertEquals(editedFoodEntry.getAmount(), newFoodEntry.getAmount());
    }

    @Test
    @DisplayName("Should throw EntryNotFoundException when trying to edit exercise entry in non-existing diary entry")
    void shouldThrowEntryNotFoundExceptionWhenTryingToEditExerciseEntryInNonExistingDiaryEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final LocalDate diaryDate = LocalDate.now();
        final short entryPosition = 0;
        final EditedExerciseEntry editedExerciseEntry = new EditedExerciseEntry(TimeUnit.HOUR, BigDecimal.TEN);
        final Executable executable = () -> diaryService.editExerciseEntry(diaryDate, entryPosition, editedExerciseEntry);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);

        //then
        final EntryNotFoundException exception = assertThrows(EntryNotFoundException.class, executable);
        assertTrue(exception.getMessage().startsWith("Diary is empty"));
    }

    @Test
    @DisplayName("Should throw EntryNotFoundException when trying to edit exercise entry in empty diary entry")
    void shouldThrowEntryNotFoundExceptionWhenTryingToEditExerciseEntryInEmptyDiaryEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final DiaryEntryEntity diaryEntry = new DiaryEntryEntity(diaryDate, user);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        final short entryPosition = 0;
        final EditedExerciseEntry editedExerciseEntry = new EditedExerciseEntry(TimeUnit.HOUR, BigDecimal.TEN);
        final Executable executable = () -> diaryService.editExerciseEntry(diaryDate, entryPosition, editedExerciseEntry);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);

        //then
        assertThrows(EntryNotFoundException.class, executable);
    }

    @Test
    @DisplayName("Should edit exercise entry")
    void shouldEditExerciseEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final DiaryEntryEntity diaryEntry = new DiaryEntryEntity(diaryDate, user);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        final short entryPosition = 0;
        final ExerciseEntryEntity oldExerciseEntry = new ExerciseEntryEntity("running",
                                                                             TimeUnit.HOUR,
                                                                             BigDecimal.ONE,
                                                                             entryPosition,
                                                                             diaryEntry);
        diaryEntry.add(oldExerciseEntry);
        final EditedExerciseEntry editedExerciseEntry = new EditedExerciseEntry(TimeUnit.MINUTE, BigDecimal.TEN);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final TimeUnit oldExerciseEntryTimeUnit = oldExerciseEntry.getTimeUnit();
        final BigDecimal oldExerciseEntryDuration = oldExerciseEntry.getAmount();
        diaryService.editExerciseEntry(diaryDate, entryPosition, editedExerciseEntry);
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

    @Test
    @DisplayName("Should throw EntryNotFoundException when trying to edit note entry in non-existing diary entry")
    void shouldThrowEntryNotFoundExceptionWhenTryingToEditNoteEntryInNonExistingDiaryEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final LocalDate diaryDate = LocalDate.now();
        final short entryPosition = 0;
        final NoteEntry editedNoteEntry = new NoteEntry("edited note");
        final Executable executable = () -> diaryService.editNoteEntry(diaryDate, entryPosition, editedNoteEntry);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);

        //then
        final EntryNotFoundException exception = assertThrows(EntryNotFoundException.class, executable);
        assertTrue(exception.getMessage().startsWith("Diary is empty"));
    }

    @Test
    @DisplayName("Should throw EntryNotFoundException when trying to edit note entry in empty diary entry")
    void shouldThrowEntryNotFoundExceptionWhenTryingToEditNoteEntryInEmptyDiaryEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final DiaryEntryEntity diaryEntry = new DiaryEntryEntity(diaryDate, user);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        final short entryPosition = 0;
        final NoteEntry editedNoteEntry = new NoteEntry("edited note");
        final Executable executable = () -> diaryService.editNoteEntry(diaryDate, entryPosition, editedNoteEntry);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);

        //then
        assertThrows(EntryNotFoundException.class, executable);
    }

    @Test
    @DisplayName("Should edit note entry")
    void shouldEditNoteEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final DiaryEntryEntity diaryEntry = new DiaryEntryEntity(diaryDate, user);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        final short entryPosition = 0;
        final NoteEntryEntity oldNoteEntry = new NoteEntryEntity("old note",
                                                                 entryPosition,
                                                                 diaryEntry);
        diaryEntry.add(oldNoteEntry);
        final NoteEntry editedNoteEntry = new NoteEntry("edited note");

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final String oldNoteContent = oldNoteEntry.getContent();
        diaryService.editNoteEntry(diaryDate, entryPosition, editedNoteEntry);
        final NoteEntryEntity newNoteEntry = diaryRepository.findDiaryEntryFetchNoteEntries(userEmail, diaryDate)
                .getNoteEntries()
                .iterator()
                .next();

        //then
        assertNotEquals(editedNoteEntry.getContent(), oldNoteContent);
        assertEquals(editedNoteEntry.getContent(), newNoteEntry.getContent());
    }

    @Test
    @DisplayName("Should throw EntryNotFoundException when trying to delete entry in non-existing diary entry")
    void shouldThrowEntryNotFoundExceptionWhenTryingToDeleteEntryInNonExistingDiaryEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final LocalDate diaryDate = LocalDate.now();
        final short entryPosition = 0;
        final Executable executable = () -> diaryService.deleteEntry(diaryDate, entryPosition);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);

        //then
        final EntryNotFoundException exception = assertThrows(EntryNotFoundException.class, executable);
        assertTrue(exception.getMessage().startsWith("Diary is empty"));
    }

    @Test
    @DisplayName("Should throw EntryNotFoundException when trying to delete entry from empty diary entry")
    void shouldThrowEntryNotFoundExceptionWhenTryingToDeleteEntryFromEmptyDiaryEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final LocalDate diaryDate = LocalDate.now();
        final short entryPosition = 0;
        final DiaryEntryEntity diaryEntry = new DiaryEntryEntity(diaryDate, user);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        final Executable executable = () -> diaryService.deleteEntry(diaryDate, entryPosition);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);

        //then
        assertThrows(EntryNotFoundException.class, executable);
    }

    @Test
    @DisplayName("Should delete only one entry")
    void shouldDeleteOnlyOneEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final LocalDate diaryDate = LocalDate.now();
        final DiaryEntryEntity diaryEntry = DiaryEntryEntitySamples.withMultipleEntries(user, diaryDate);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        final short entryPosition = 0;

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final int entriesCountBeforeChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate)
                .getSize();
        diaryService.deleteEntry(diaryDate, entryPosition);
        final int entriesCountAfterChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate)
                .getSize();

        //then
        assertEquals(entriesCountBeforeChange - 1, entriesCountAfterChange);
    }

    @Test
    @DisplayName("Should delete last entry without changing previous entries' positions")
    void shouldDeleteLastEntryWithoutChangingPreviousEntriesPositions() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final LocalDate diaryDate = LocalDate.now();
        final DiaryEntryEntity diaryEntry = DiaryEntryEntitySamples.withMultipleEntries(user, diaryDate);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        final short positionToDelete = (short) (diaryEntry.getSize() - 1);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final List<Sortable> previousEntriesBeforeChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate)
                .entries()
                .filter(entry -> entry.getPosition() != positionToDelete)
                .collect(Collectors.toList());
        diaryService.deleteEntry(diaryDate, positionToDelete);
        final List<Sortable> previousEntriesAfterChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate)
                .entries()
                .collect(Collectors.toList());

        //then
        assertIterableEquals(previousEntriesBeforeChange, previousEntriesAfterChange);
    }

    @Test
    @DisplayName("Should delete first entry and move up every other entry")
    void shouldDeleteFirstEntryAndMoveUpEveryOtherEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final LocalDate diaryDate = LocalDate.now();
        final DiaryEntryEntity diaryEntry = DiaryEntryEntitySamples.withMultipleEntries(user, diaryDate);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        final short positionToDelete = 0;

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final List<Short> otherEntriesPositionsBeforeChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate)
                .entries()
                .map(Sortable::getPosition)
                .filter(position -> position != positionToDelete)
                .collect(Collectors.toList());
        diaryService.deleteEntry(diaryDate, positionToDelete);
        final List<Short> otherEntriesPositionsAfterChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate)
                .entries()
                .map(Sortable::getPosition)
                .collect(Collectors.toList());

        //then
        for (int i = 0; i < otherEntriesPositionsBeforeChange.size(); ++i) {
            final short entryPositionBeforeChange = otherEntriesPositionsBeforeChange.get(i);
            final short entryPositionAfterChange = otherEntriesPositionsAfterChange.get(i);
            assertEquals(entryPositionBeforeChange - 1, entryPositionAfterChange);
        }
    }

    @Test
    @DisplayName("Should throw EntryNotFoundException when trying to move entry in non-existing diary entry")
    void shouldThrowEntryNotFoundExceptionWhenTryingToMoveEntryInNonExistingDiaryEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final LocalDate diaryDate = LocalDate.now();
        final short oldPosition = 0;
        final short newPosition = 1;
        final Executable executable = () -> diaryService.moveEntry(diaryDate,
                                                                   oldPosition,
                                                                   newPosition);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);

        //then
        final EntryNotFoundException exception = assertThrows(EntryNotFoundException.class, executable);
        assertTrue(exception.getMessage().startsWith("Diary is empty"));
    }

    @Test
    @DisplayName("Should throw EntryNotFoundException when old position does not match any entry")
    void shouldThrowEntryNotFoundExceptionWhenOldPositionDoesNotMatchAnyEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final LocalDate diaryDate = LocalDate.now();
        final short oldPosition = 200;
        final short newPosition = 0;
        final DiaryEntryEntity diaryEntry = DiaryEntryEntitySamples.withMultipleEntries(user, diaryDate);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        final Executable executable = () -> diaryService.moveEntry(diaryDate,
                                                                   oldPosition,
                                                                   newPosition);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);

        //then
        final EntryNotFoundException exception = assertThrows(EntryNotFoundException.class, executable);
        assertTrue(exception.getMessage().contains("old position"));
    }

    @Test
    @DisplayName("Should throw EntryNotFoundException when new position does not match any entry")
    void shouldThrowEntryNotFoundExceptionWhenNewPositionDoesNotMatchAnyEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final LocalDate diaryDate = LocalDate.now();
        final short oldPosition = 0;
        final short newPosition = 200;
        final DiaryEntryEntity diaryEntry = DiaryEntryEntitySamples.withMultipleEntries(user, diaryDate);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        final Executable executable = () -> diaryService.moveEntry(diaryDate,
                                                                   oldPosition,
                                                                   newPosition);

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);

        //then
        final EntryNotFoundException exception = assertThrows(EntryNotFoundException.class, executable);
        assertTrue(exception.getMessage().contains("new position"));
    }

    @Test
    @DisplayName("Should not change entries when old position matches new position")
    void shouldNotChangeEntriesWhenOldPositionMatchesNewPosition() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final LocalDate diaryDate = LocalDate.now();
        final short oldPosition = 0;
        final short newPosition = 0;
        final DiaryEntryEntity diaryEntry = DiaryEntryEntitySamples.withMultipleEntries(user, diaryDate);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final List<Sortable> entriesBeforeChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate)
                .entries()
                .collect(Collectors.toList());
        diaryService.moveEntry(diaryDate, oldPosition, newPosition);
        final List<Sortable> entriesAfterChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate)
                .entries()
                .collect(Collectors.toList());

        //then
        assertIterableEquals(entriesBeforeChange, entriesAfterChange);
    }

    @Test
    @DisplayName("Should change entry position and move up entries in appropriate range")
    void shouldChangeEntryPositionAndMoveUpEntriesInAppropriateRange() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final LocalDate diaryDate = LocalDate.now();
        final short oldPosition = 1;
        final short newPosition = 4;
        final DiaryEntryEntity diaryEntry = DiaryEntryEntitySamples.withMultipleEntries(user, diaryDate);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final DiaryEntryEntity diaryEntryBeforeChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate);
        final Sortable firstEntryBeforeChange = getEntry(diaryEntryBeforeChange, 0);
        final Sortable lastEntryBeforeChange = getEntry(diaryEntryBeforeChange, diaryEntryBeforeChange.getSize() - 1);
        final Sortable oldPositionEntryBeforeChange = getEntry(diaryEntryBeforeChange, oldPosition);
        final List<Sortable> entriesToBeMovedUpBeforeChange = diaryEntryBeforeChange.entries()
                .filter(entry -> entry.getPosition() > oldPosition && entry.getPosition() <= newPosition)
                .collect(Collectors.toList());
        diaryService.moveEntry(diaryDate, oldPosition, newPosition);
        final DiaryEntryEntity diaryEntryAfterChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate);
        final Sortable firstEntryAfterChange = getEntry(diaryEntryAfterChange, 0);
        final Sortable lastEntryAfterChange = getEntry(diaryEntryAfterChange, diaryEntryAfterChange.getSize() - 1);
        final Sortable newPositionEntryAfterChange = getEntry(diaryEntryAfterChange, newPosition);
        final List<Sortable> entriesToBeMovedUpAfterChange = diaryEntryAfterChange.entries()
                .filter(entry -> entry.getPosition() >= oldPosition && entry.getPosition() < newPosition)
                .collect(Collectors.toList());

        //then
        assertEquals(firstEntryBeforeChange, firstEntryAfterChange);
        assertEquals(lastEntryBeforeChange, lastEntryAfterChange);
        assertEquals(oldPositionEntryBeforeChange, newPositionEntryAfterChange);
        for (int i = 0; i < entriesToBeMovedUpBeforeChange.size(); ++i) {
            final Sortable entryBeforeMovingUp = entriesToBeMovedUpBeforeChange.get(i);
            final Sortable entryAfterMovingUp = entriesToBeMovedUpAfterChange.get(i);
            assertEquals(entryBeforeMovingUp, entryAfterMovingUp);
        }
    }

    @Test
    @DisplayName("Should change entry position and move down entries in appropriate range")
    void shouldChangeEntryPositionAndMoveDownEntriesInAppropriateRange() {
        //given
        final String userEmail = "foo@bar.com";
        final String userPassword = "password";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final LocalDate diaryDate = LocalDate.now();
        final short oldPosition = 4;
        final short newPosition = 1;
        final DiaryEntryEntity diaryEntry = DiaryEntryEntitySamples.withMultipleEntries(user, diaryDate);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));

        //when
        userRepository.save(user);
        authenticationService.authenticate(userEmail, userPassword);
        final DiaryEntryEntity diaryEntryBeforeChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate);
        final Sortable firstEntryBeforeChange = getEntry(diaryEntryBeforeChange, 0);
        final Sortable lastEntryBeforeChange = getEntry(diaryEntryBeforeChange, diaryEntryBeforeChange.getSize() - 1);
        final Sortable oldPositionEntryBeforeChange = getEntry(diaryEntryBeforeChange, oldPosition);
        final List<Sortable> entriesToBeMovedDownBeforeChange = diaryEntryBeforeChange.entries()
                .filter(entry -> entry.getPosition() >= oldPosition && entry.getPosition() < newPosition)
                .collect(Collectors.toList());
        diaryService.moveEntry(diaryDate, oldPosition, newPosition);
        final DiaryEntryEntity diaryEntryAfterChange = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate);
        final Sortable firstEntryAfterChange = getEntry(diaryEntryAfterChange, 0);
        final Sortable lastEntryAfterChange = getEntry(diaryEntryAfterChange, diaryEntryAfterChange.getSize() - 1);
        final Sortable newPositionEntryAfterChange = getEntry(diaryEntryAfterChange, newPosition);
        final List<Sortable> entriesToBeMovedDownAfterChange = diaryEntryAfterChange.entries()
                .filter(entry -> entry.getPosition() > oldPosition && entry.getPosition() <= newPosition)
                .collect(Collectors.toList());

        //then
        assertEquals(firstEntryBeforeChange, firstEntryAfterChange);
        assertEquals(lastEntryBeforeChange, lastEntryAfterChange);
        assertEquals(oldPositionEntryBeforeChange, newPositionEntryAfterChange);
        for (int i = 0; i < entriesToBeMovedDownBeforeChange.size(); ++i) {
            final Sortable entryBeforeMovingDown = entriesToBeMovedDownBeforeChange.get(i);
            final Sortable entryAfterMovingDown = entriesToBeMovedDownAfterChange.get(i);
            assertEquals(entryBeforeMovingDown, entryAfterMovingDown);
        }
    }

    private static Sortable getEntry(final DiaryEntryEntity diaryEntry, final int position) {
        return diaryEntry.entries()
                .filter(entry -> entry.getPosition() == position)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

}