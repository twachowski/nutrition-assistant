package pl.polsl.wachowski.nutritionassistant.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUserByEmail(final String email);

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.userCredentials WHERE u.email = :email")
    UserEntity findUserByEmailFetchCredentials(final String email);

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.userBiometrics WHERE u.email = :email")
    UserEntity findUserByEmailFetchBiometrics(final String email);

}
