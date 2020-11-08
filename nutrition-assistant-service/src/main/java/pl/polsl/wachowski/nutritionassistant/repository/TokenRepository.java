package pl.polsl.wachowski.nutritionassistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.wachowski.nutritionassistant.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.db.user.VerificationTokenEntity;

@Repository
public interface TokenRepository extends JpaRepository<VerificationTokenEntity, Long> {

    VerificationTokenEntity findVerificationTokenByValue(final String token);

    void deleteAllByUser(final UserEntity user);

}
