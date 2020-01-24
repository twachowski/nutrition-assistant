package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.db.entry.NoteEntry;
import pl.polsl.wachowski.nutritionassistant.dto.diary.note.NoteEntryDTO;
import pl.polsl.wachowski.nutritionassistant.exception.entry.EntryNotFoundException;
import pl.polsl.wachowski.nutritionassistant.repository.NoteRepository;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(final NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void editNoteEntry(final List<NoteEntry> noteEntries, final NoteEntryDTO editedEntry) {
        final NoteEntry noteEntry = noteEntries
                .stream()
                .filter(entry -> entry.getPosition().equals(editedEntry.getPosition()))
                .findFirst()
                .orElseThrow(EntryNotFoundException::new);
        noteEntry.setContent(editedEntry.getContent());

        noteRepository.save(noteEntry);
    }

}
