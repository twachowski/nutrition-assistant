package pl.polsl.wachowski.nutritionassistant.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.DiaryEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.views.*;

import java.time.LocalDate;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntryEntity, Long> {

    @Query("SELECT u.id, d, d.foodEntries, d.exerciseEntries, d.noteEntries " +
                   "FROM DiaryEntryEntity d INNER JOIN d.user u " +
                   "WHERE u.email = :user AND d.date = :date")
    UserDiaryEntrySimpleView findDiaryEntryByUserAndDate(final String user, final LocalDate date);

    @Query("SELECT u, d, d.foodEntries, d.exerciseEntries, d.noteEntries " +
                   "FROM DiaryEntryEntity d INNER JOIN d.user u " +
                   "WHERE u.email = :user AND d.date = :date")
    UserDiaryEntryView findUserAndDiaryEntryByUserAndDate(final String user, final LocalDate date);

    @Query("SELECT u.id, d.id, d.foodEntries " +
                   "FROM DiaryEntryEntity d INNER JOIN d.user u " +
                   "WHERE u.email = :user AND d.date = :date")
    UserDiaryFoodEntriesView findUserAndFoodEntriesByUserAndDate(final String user, final LocalDate date);

    @Query("SELECT u.id, d.id, d.exerciseEntries " +
                   "FROM DiaryEntryEntity d INNER JOIN d.user u " +
                   "WHERE u.email = :user AND d.date = :date")
    UserDiaryExerciseEntriesView findUserAndExerciseEntriesByUserAndDate(final String user, final LocalDate date);

    @Query("SELECT u.id, d.id, d.noteEntries " +
                   "FROM DiaryEntryEntity d INNER JOIN d.user u " +
                   "WHERE u.email = :user AND d.date = :date")
    UserDiaryNoteEntriesView findUserAndNoteEntriesByUserAndDate(final String user, final LocalDate date);

}
