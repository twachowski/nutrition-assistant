package pl.polsl.wachowski.nutritionassistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.wachowski.nutritionassistant.db.user.User;
import pl.polsl.wachowski.nutritionassistant.db.user.VerificationToken;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findVerificationTokenByValue(final String token);

    void deleteAllByUser(final User user);

}
