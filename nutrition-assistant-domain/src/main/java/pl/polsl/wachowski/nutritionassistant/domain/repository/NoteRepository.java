package pl.polsl.wachowski.nutritionassistant.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.NoteEntryEntity;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntryEntity, Long> {
}
