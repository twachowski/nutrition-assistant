package pl.polsl.wachowski.nutritionassistant.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.VerificationTokenEntity;

@Repository
public interface TokenRepository extends JpaRepository<VerificationTokenEntity, Long> {

    VerificationTokenEntity findVerificationTokenByValue(final String token);

    @Modifying
    @Query(value = "DELETE FROM verification_token t WHERE t.user_id IN " +
            "(SELECT t2.user_id FROM " +
            "(SELECT vt.user_id FROM verification_token vt " +
            "INNER JOIN user u on vt.user_id = u.id " +
            "WHERE u.status = :#{#userStatus.ordinal()}) AS t2)",
           nativeQuery = true)
    int deleteVerificationTokensByUserStatus(UserEntity.UserStatus userStatus);

    @Modifying
    @Query(value = "DELETE FROM VerificationTokenEntity t WHERE t.expiryDate < current_timestamp")
    int deleteExpiredVerificationTokens();

}
