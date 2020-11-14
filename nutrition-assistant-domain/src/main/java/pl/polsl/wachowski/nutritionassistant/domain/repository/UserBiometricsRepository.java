package pl.polsl.wachowski.nutritionassistant.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserBiometricsEntity;

import java.util.Optional;

@Repository
public interface UserBiometricsRepository extends JpaRepository<UserBiometricsEntity, Long> {

    @Query("SELECT ub FROM UserBiometricsEntity ub INNER JOIN ub.user u WHERE u.email = :email")
    Optional<UserBiometricsEntity> findUserBiometricsByUserEmail(String email);

}
