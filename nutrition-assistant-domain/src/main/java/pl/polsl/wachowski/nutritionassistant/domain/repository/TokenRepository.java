package pl.polsl.wachowski.nutritionassistant.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.VerificationTokenEntity;

@Repository
public interface TokenRepository extends JpaRepository<VerificationTokenEntity, Long> {

    VerificationTokenEntity findVerificationTokenByValue(final String token);

    @Modifying
    @Query(value = "DELETE t FROM verification_token t INNER JOIN user u on t.user_id = u.id WHERE u.status = :userStatus",
           nativeQuery = true)
    int deleteVerificationTokensByUserStatus(int userStatus);

    @Modifying
    @Query(value = "DELETE FROM VerificationTokenEntity t WHERE t.expiryDate < current_timestamp")
    int deleteExpiredVerificationTokens();

}
