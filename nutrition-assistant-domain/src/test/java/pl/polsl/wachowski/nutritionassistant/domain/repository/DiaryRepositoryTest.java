package pl.polsl.wachowski.nutritionassistant.domain.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.DiaryEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.samples.DiaryEntryEntitySamples;
import pl.polsl.wachowski.nutritionassistant.domain.samples.UserEntitySamples;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {DiaryRepository.class, UserRepository.class})
@EnableJpaRepositories(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.repository")
@EntityScan(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.db")
class DiaryRepositoryTest {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    @Autowired
    public DiaryRepositoryTest(final DiaryRepository diaryRepository, final UserRepository userRepository) {
        this.diaryRepository = diaryRepository;
        this.userRepository = userRepository;
    }

    @Test
    @DisplayName("Should return null if user with given email does not exist and food entries are fetched")
    void shouldReturnNullIfUserWithGivenEmailDoesNotExistAndFoodEntriesAreFetched() {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity existingUser = UserEntitySamples.activeUser("foo@bar.com");
        final String missingUserEmail = "bar@foo.com";

        //when
        userRepository.save(existingUser);
        diaryRepository.save(DiaryEntryEntitySamples.ofTodayWithAllEntries(existingUser));
        final DiaryEntryEntity diaryEntry = diaryRepository.findDiaryEntryFetchFoodEntries(missingUserEmail, diaryDate);

        //then
        assertNull(diaryEntry);
    }

    @Test
    @DisplayName("Should return null if user with given email does not exist and exercise entries are fetched")
    void shouldReturnNullIfUserWithGivenEmailDoesNotExistAndExerciseEntriesAreFetched() {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity existingUser = UserEntitySamples.activeUser("foo@bar.com");
        final String missingUserEmail = "bar@foo.com";

        //when
        userRepository.save(existingUser);
        diaryRepository.save(DiaryEntryEntitySamples.ofTodayWithAllEntries(existingUser));
        final DiaryEntryEntity diaryEntry = diaryRepository.findDiaryEntryFetchExerciseEntries(missingUserEmail, diaryDate);

        //then
        assertNull(diaryEntry);
    }

    @Test
    @DisplayName("Should return null if user with given email does not exist and note entries are fetched")
    void shouldReturnNullIfUserWithGivenEmailDoesNotExistAndNoteEntriesAreFetched() {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity existingUser = UserEntitySamples.activeUser("foo@bar.com");
        final String missingUserEmail = "bar@foo.com";

        //when
        userRepository.save(existingUser);
        diaryRepository.save(DiaryEntryEntitySamples.ofTodayWithAllEntries(existingUser));
        final DiaryEntryEntity diaryEntry = diaryRepository.findDiaryEntryFetchNoteEntries(missingUserEmail, diaryDate);

        //then
        assertNull(diaryEntry);
    }

    @Test
    @DisplayName("Should return null if diary entry with given date does not exist and food entries are fetched")
    void shouldReturnNullIfDiaryEntryWithGivenDateDoesNotExistAndFoodEntriesAreFetched() {
        //given
        final LocalDate diaryDate = LocalDate.now().plusDays(1);
        final String userEmail = "foo@bar.com";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);

        //when
        userRepository.save(user);
        diaryRepository.save(DiaryEntryEntitySamples.ofTodayWithAllEntries(user));
        final DiaryEntryEntity diaryEntry = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate);

        //then
        assertNull(diaryEntry);
    }

    @Test
    @DisplayName("Should return null if diary entry with given date does not exist and exercise entries are fetched")
    void shouldReturnNullIfDiaryEntryWithGivenDateDoesNotExistAndExerciseEntriesAreFetched() {
        //given
        final LocalDate diaryDate = LocalDate.now().plusDays(1);
        final String userEmail = "foo@bar.com";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);

        //when
        userRepository.save(user);
        diaryRepository.save(DiaryEntryEntitySamples.ofTodayWithAllEntries(user));
        final DiaryEntryEntity diaryEntry = diaryRepository.findDiaryEntryFetchExerciseEntries(userEmail, diaryDate);

        //then
        assertNull(diaryEntry);
    }

    @Test
    @DisplayName("Should return null if diary entry with given date does not exist and note entries are fetched")
    void shouldReturnNullIfDiaryEntryWithGivenDateDoesNotExistAndNoteEntriesAreFetched() {
        //given
        final LocalDate diaryDate = LocalDate.now().plusDays(1);
        final String userEmail = "foo@bar.com";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);

        //when
        userRepository.save(user);
        diaryRepository.save(DiaryEntryEntitySamples.ofTodayWithAllEntries(user));
        final DiaryEntryEntity diaryEntry = diaryRepository.findDiaryEntryFetchNoteEntries(userEmail, diaryDate);

        //then
        assertNull(diaryEntry);
    }

    @Test
    @DisplayName("Should return diary entry with non-empty sub-entries when food entries are fetched")
    void shouldReturnDiaryEntryWithNonEmptySubEntriesWhenFoodEntriesAreFetched() {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String userEmail = "foo@bar.com";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);

        //when
        userRepository.save(user);
        diaryRepository.save(DiaryEntryEntitySamples.ofTodayWithAllEntries(user));
        final DiaryEntryEntity diaryEntry = diaryRepository.findDiaryEntryFetchFoodEntries(userEmail, diaryDate);

        //then
        assertFalse(diaryEntry.getFoodEntries().isEmpty());
        assertFalse(diaryEntry.getExerciseEntries().isEmpty());
        assertFalse(diaryEntry.getNoteEntries().isEmpty());
    }

    @Test
    @DisplayName("Should return diary entry with non-empty sub-entries when exercise entries are fetched")
    void shouldReturnDiaryEntryWithNonEmptySubEntriesWhenExerciseEntriesAreFetched() {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String userEmail = "foo@bar.com";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);

        //when
        userRepository.save(user);
        diaryRepository.save(DiaryEntryEntitySamples.ofTodayWithAllEntries(user));
        final DiaryEntryEntity diaryEntry = diaryRepository.findDiaryEntryFetchExerciseEntries(userEmail, diaryDate);

        //then
        assertFalse(diaryEntry.getFoodEntries().isEmpty());
        assertFalse(diaryEntry.getExerciseEntries().isEmpty());
        assertFalse(diaryEntry.getNoteEntries().isEmpty());
    }

    @Test
    @DisplayName("Should return diary entry with non-empty sub-entries when note entries are fetched")
    void shouldReturnDiaryEntryWithNonEmptySubEntriesWhenNoteEntriesAreFetched() {
        //given
        final LocalDate diaryDate = LocalDate.now();
        final String userEmail = "foo@bar.com";
        final UserEntity user = UserEntitySamples.activeUser(userEmail);

        //when
        userRepository.save(user);
        diaryRepository.save(DiaryEntryEntitySamples.ofTodayWithAllEntries(user));
        final DiaryEntryEntity diaryEntry = diaryRepository.findDiaryEntryFetchNoteEntries(userEmail, diaryDate);

        //then
        assertFalse(diaryEntry.getFoodEntries().isEmpty());
        assertFalse(diaryEntry.getExerciseEntries().isEmpty());
        assertFalse(diaryEntry.getNoteEntries().isEmpty());
    }

}