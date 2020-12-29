package pl.polsl.wachowski.nutritionassistant.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.targets.HighlightedTargetsEntity;

import java.util.Optional;

@Repository
public interface HighlightedTargetsRepository extends JpaRepository<HighlightedTargetsEntity, Long> {

    @Query("SELECT ht FROM HighlightedTargetsEntity ht INNER JOIN ht.user u WHERE u.email = :userEmail")
    Optional<HighlightedTargetsEntity> findHighlightedTargetsByUserEmail(String userEmail);

}
