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
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.note.NoteEntry;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.DiaryEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.NoteEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.repository.DiaryRepository;
import pl.polsl.wachowski.nutritionassistant.domain.repository.NoteRepository;
import pl.polsl.wachowski.nutritionassistant.domain.repository.UserRepository;
import pl.polsl.wachowski.nutritionassistant.exception.entry.EntryNotFoundException;
import pl.polsl.wachowski.nutritionassistant.samples.UserEntitySamples;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@ContextConfiguration(classes = {UserRepository.class, DiaryRepository.class, NoteRepository.class})
@EnableJpaRepositories(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.repository")
@EntityScan(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.db")
class NoteServiceTest {

    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;
    private final NoteService noteService;

    @Autowired
    NoteServiceTest(final UserRepository userRepository,
                    final DiaryRepository diaryRepository,
                    final NoteRepository noteRepository) {
        this.userRepository = userRepository;
        this.diaryRepository = diaryRepository;
        this.noteService = new NoteService(noteRepository);
    }

    @Test
    @DisplayName("Should throw EntryNotFoundException when no note entry has given position")
    void shouldThrowEntryNotFoundExceptionWhenNoNoteEntryHasGivenPosition() {
        //given
        final List<NoteEntryEntity> noteEntries = Collections.emptyList();
        final short entryPosition = 0;
        final NoteEntry editedNoteEntry = new NoteEntry("note");
        final Executable executable = () -> noteService.editNoteEntry(noteEntries, entryPosition, editedNoteEntry);

        //when

        //then
        assertThrows(EntryNotFoundException.class, executable);
    }

    @Test
    @DisplayName("Should edit note entry")
    void shouldEditNoteEntry() {
        //given
        final String userEmail = "foo@bar.com";
        final LocalDate diaryDate = LocalDate.now();
        final UserEntity user = UserEntitySamples.activeUser(userEmail);
        final DiaryEntryEntity diaryEntry = new DiaryEntryEntity(diaryDate, user);
        user.setDiaryEntries(Collections.singletonList(diaryEntry));
        final short noteEntryPosition = 0;
        diaryEntry.add(new NoteEntryEntity("oldContent",
                                           noteEntryPosition,
                                           diaryEntry));
        final NoteEntry editedNoteEntry = new NoteEntry("newContent");

        //when
        userRepository.save(user);
        final String oldNoteEntryContent = diaryEntry.getNoteEntries()
                .iterator()
                .next()
                .getContent();
        noteService.editNoteEntry(diaryEntry.getNoteEntries(), noteEntryPosition, editedNoteEntry);
        final String newNoteEntryContent = diaryRepository.findDiaryEntryFetchNoteEntries(userEmail, diaryDate)
                .getNoteEntries()
                .iterator()
                .next()
                .getContent();

        //then
        assertNotEquals(editedNoteEntry.getContent(), oldNoteEntryContent);
        assertEquals(editedNoteEntry.getContent(), newNoteEntryContent);
    }
}