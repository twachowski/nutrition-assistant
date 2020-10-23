package pl.polsl.wachowski.nutritionassistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(final String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userCredentials WHERE u.email = :email")
    User findUserByEmailFetchCredentials(final String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userBiometrics WHERE u.email = :email")
    User findUserByEmailFetchBiometrics(final String email);

}
