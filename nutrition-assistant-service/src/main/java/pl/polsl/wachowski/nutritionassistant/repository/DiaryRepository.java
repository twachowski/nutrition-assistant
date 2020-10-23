package pl.polsl.wachowski.nutritionassistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.wachowski.nutritionassistant.db.entry.DiaryEntry;
import pl.polsl.wachowski.nutritionassistant.db.entry.views.*;

import java.time.LocalDate;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntry, Long> {

    @Query("SELECT u.id, d, d.foodEntries, d.exerciseEntries, d.noteEntries " +
                   "FROM DiaryEntry d INNER JOIN d.user u " +
                   "WHERE u.email = :user AND d.date = :date")
    UserDiaryEntrySimpleView findDiaryEntryByUserAndDate(final String user, final LocalDate date);

    @Query("SELECT u, d, d.foodEntries, d.exerciseEntries, d.noteEntries " +
                   "FROM DiaryEntry d INNER JOIN d.user u " +
                   "WHERE u.email = :user AND d.date = :date")
    UserDiaryEntryView findUserAndDiaryEntryByUserAndDate(final String user, final LocalDate date);

    @Query("SELECT u.id, d.id, d.foodEntries " +
                   "FROM DiaryEntry d INNER JOIN d.user u " +
                   "WHERE u.email = :user AND d.date = :date")
    UserDiaryFoodEntriesView findUserAndFoodEntriesByUserAndDate(final String user, final LocalDate date);

    @Query("SELECT u.id, d.id, d.exerciseEntries " +
                   "FROM DiaryEntry d INNER JOIN d.user u " +
                   "WHERE u.email = :user AND d.date = :date")
    UserDiaryExerciseEntriesView findUserAndExerciseEntriesByUserAndDate(final String user, final LocalDate date);

    @Query("SELECT u.id, d.id, d.noteEntries " +
                   "FROM DiaryEntry d INNER JOIN d.user u " +
                   "WHERE u.email = :user AND d.date = :date")
    UserDiaryNoteEntriesView findUserAndNoteEntriesByUserAndDate(final String user, final LocalDate date);

}
