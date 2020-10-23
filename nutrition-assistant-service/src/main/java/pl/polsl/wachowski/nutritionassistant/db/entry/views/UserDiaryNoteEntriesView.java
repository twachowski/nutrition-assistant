package pl.polsl.wachowski.nutritionassistant.db.entry.views;

import org.springframework.beans.factory.annotation.Value;
import pl.polsl.wachowski.nutritionassistant.db.entry.NoteEntryEntity;

import java.util.List;

public interface UserDiaryNoteEntriesView {

    @Value("#{target.user.id}")
    Long getUserId();

    @Value("#{target.id}")
    Long getDiaryId();

    List<NoteEntryEntity> getNoteEntries();

}
