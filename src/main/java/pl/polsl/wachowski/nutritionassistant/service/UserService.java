package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.db.user.User;
import pl.polsl.wachowski.nutritionassistant.db.user.UserCredentials;
import pl.polsl.wachowski.nutritionassistant.exception.UserExistsException;
import pl.polsl.wachowski.nutritionassistant.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(
            final UserRepository userRepository,
            final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void createUser(final User user) throws UserExistsException {
        if (userExists(user.getEmail())) {
            throw new UserExistsException("Email already exists");
        }
        encodeUserPassword(user.getUserCredentials());
        userRepository.save(user);
    }

    private boolean userExists(final String email) {
        return userRepository.findUserByEmail(email) != null;
    }

    private void encodeUserPassword(final UserCredentials userCredentials) {
        final String encodedPassword = bCryptPasswordEncoder.encode(userCredentials.getPassword());
        userCredentials.setPassword(encodedPassword);
    }

}
