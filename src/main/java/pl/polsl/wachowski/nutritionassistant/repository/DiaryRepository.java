package pl.polsl.wachowski.nutritionassistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.wachowski.nutritionassistant.db.entry.DiaryEntry;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import java.time.LocalDate;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntry, Long> {

    DiaryEntry findDiaryEntryByUserAndDate(final User user, final LocalDate date);

    @Query("SELECT d FROM DiaryEntry d" +
            " LEFT JOIN FETCH d.foodEntries" +
            " WHERE d.user = :user AND d.date = :date")
    DiaryEntry findDiaryEntryByUserAndDateFetchFoodEntries(final User user, final LocalDate date);

    @Query("SELECT d FROM DiaryEntry d" +
            " LEFT JOIN FETCH d.exerciseEntries" +
            " WHERE d.user = :user AND d.date = :date")
    DiaryEntry findDiaryEntryByUserAndDateFetchExerciseEntries(final User user, final LocalDate date);

    @Query("SELECT d FROM DiaryEntry d" +
            " LEFT JOIN FETCH d.noteEntries" +
            " WHERE d.user = :user AND d.date = :date")
    DiaryEntry findDiaryEntryByUserAndDateFetchNoteEntries(final User user, final LocalDate date);

}
