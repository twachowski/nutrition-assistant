package pl.polsl.wachowski.nutritionassistant.db.entry.views;

import org.springframework.beans.factory.annotation.Value;
import pl.polsl.wachowski.nutritionassistant.db.entry.*;

import java.util.List;
import java.util.stream.Stream;

public interface UserDiaryEntrySimpleView {

    @Value("#{target.user.id}")
    String getUserId();

    DiaryEntry get();

    List<FoodEntryEntity> getFoodEntries();

    List<ExerciseEntryEntity> getExerciseEntries();

    List<NoteEntryEntity> getNoteEntries();

    default Stream<Sortable> entries() {
        return Stream.of(getFoodEntries(), getExerciseEntries(), getNoteEntries())
                .flatMap(List::stream);
    }

}
