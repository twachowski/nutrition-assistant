package pl.polsl.wachowski.nutritionassistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.wachowski.nutritionassistant.db.entry.NoteEntry;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntry, Long> {
}
