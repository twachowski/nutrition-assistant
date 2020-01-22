package pl.polsl.wachowski.nutritionassistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.wachowski.nutritionassistant.db.entry.DiaryEntry;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import java.time.LocalDate;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntry, Long> {

    DiaryEntry findDiaryEntryByUserAndDate(final User user, final LocalDate date);

}
