package pl.polsl.wachowski.nutritionassistant.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.ExerciseEntryEntity;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseEntryEntity, Long> {
}
