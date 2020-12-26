package pl.polsl.wachowski.nutritionassistant.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.DiaryEntryEntity;

import java.time.LocalDate;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntryEntity, Long> {

    @Query("SELECT d FROM DiaryEntryEntity d LEFT JOIN FETCH d.foodEntries " +
                   "WHERE d.user.email = :userEmail AND d.date = :diaryDate")
    DiaryEntryEntity findDiaryEntryFetchFoodEntries(final String userEmail, final LocalDate diaryDate);

    @Query("SELECT d FROM DiaryEntryEntity d LEFT JOIN FETCH d.exerciseEntries " +
                   "WHERE d.user.email = :userEmail AND d.date = :diaryDate")
    DiaryEntryEntity findDiaryEntryFetchExerciseEntries(final String userEmail, final LocalDate diaryDate);

    @Query("SELECT d FROM DiaryEntryEntity d LEFT JOIN FETCH d.noteEntries " +
                   "WHERE d.user.email = :userEmail AND d.date = :diaryDate")
    DiaryEntryEntity findDiaryEntryFetchNoteEntries(final String userEmail, final LocalDate diaryDate);

}
