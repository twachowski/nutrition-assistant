package pl.polsl.wachowski.nutritionassistant.domain.db.entry.views;

import org.springframework.beans.factory.annotation.Value;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.DiaryEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.ExerciseEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.FoodEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.NoteEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;

import java.util.List;

public interface UserDiaryEntryView {

    @Value("#{target.user}")
    UserEntity getUser();

    DiaryEntryEntity getDiaryEntry();

    List<FoodEntryEntity> getFoodEntries();

    List<ExerciseEntryEntity> getExerciseEntries();

    List<NoteEntryEntity> getNoteEntries();

    default int getEntriesCount() {
        return getFoodEntries().size() + getExerciseEntries().size() + getNoteEntries().size();
    }

}
