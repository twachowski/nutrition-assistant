package pl.polsl.wachowski.nutritionassistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(final String email);

}
