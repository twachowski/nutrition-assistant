package pl.polsl.wachowski.nutritionassistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.wachowski.nutritionassistant.db.user.User;
import pl.polsl.wachowski.nutritionassistant.db.user.VerificationToken;

public interface TokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findVerificationTokenByValue(final String token);

    void deleteAllByUser(final User user);

}
