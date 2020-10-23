package pl.polsl.wachowski.nutritionassistant.db.entry.views;

import org.springframework.beans.factory.annotation.Value;
import pl.polsl.wachowski.nutritionassistant.db.entry.ExerciseEntryEntity;

import java.util.List;

public interface UserDiaryExerciseEntriesView {

    @Value("#{target.user.id}")
    Long getUserId();

    @Value("#{target.id}")
    Long getDiaryId();

    List<ExerciseEntryEntity> getExerciseEntries();

}
