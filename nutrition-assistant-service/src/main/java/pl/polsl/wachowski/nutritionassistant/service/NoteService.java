package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.note.NoteEntry;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.NoteEntryEntity;
import pl.polsl.wachowski.nutritionassistant.exception.entry.EntryNotFoundException;
import pl.polsl.wachowski.nutritionassistant.domain.repository.NoteRepository;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(final NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void editNoteEntry(final List<NoteEntryEntity> noteEntries,
                              final short entryPosition,
                              final NoteEntry editedNoteEntry) {
        final NoteEntryEntity noteEntry = noteEntries.stream()
                .filter(entry -> entry.getPosition().equals(entryPosition))
                .findFirst()
                .orElseThrow(() -> new EntryNotFoundException("There is no note entry at position " + entryPosition));
        noteEntry.setContent(editedNoteEntry.getContent());
        noteRepository.save(noteEntry);
    }

}
