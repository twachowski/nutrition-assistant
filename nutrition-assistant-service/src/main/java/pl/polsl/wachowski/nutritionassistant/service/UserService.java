package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.wachowski.nutritionassistant.db.user.User;
import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometricsEntity;
import pl.polsl.wachowski.nutritionassistant.db.user.UserCredentials;
import pl.polsl.wachowski.nutritionassistant.db.user.VerificationToken;
import pl.polsl.wachowski.nutritionassistant.exception.UserAlreadyActiveException;
import pl.polsl.wachowski.nutritionassistant.exception.UserExistsException;
import pl.polsl.wachowski.nutritionassistant.exception.UserNotFoundException;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenExpiredException;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenNotFoundException;
import pl.polsl.wachowski.nutritionassistant.repository.TokenRepository;
import pl.polsl.wachowski.nutritionassistant.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(final UserRepository userRepository,
                       final TokenRepository tokenRepository,
                       final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(final String email, final String password) throws UserExistsException {
        if (userExists(email)) {
            throw new UserExistsException("User with given email already exists");
        }
        final User user = new User(email);

        final String encodedPassword = passwordEncoder.encode(password);
        final UserCredentials userCredentials = new UserCredentials(encodedPassword, user);
        user.setUserCredentials(userCredentials);

        final UserBiometricsEntity userBiometrics = UserBiometricsEntity.getDefault(user);
        user.setUserBiometrics(userBiometrics);

        return userRepository.save(user);
    }

    @Transactional
    public void activateUser(final String token) throws VerificationTokenNotFoundException,
                                                        VerificationTokenExpiredException,
                                                        UserAlreadyActiveException {
        final VerificationToken verificationToken = findVerificationToken(token);
        final User user = verificationToken.getUser();
        if (user.isActive()) {
            throw new UserAlreadyActiveException();
        }

        user.activate();
        tokenRepository.deleteAllByUser(user);  //TODO do it asynchronously with event publisher
    }

    public void createVerificationToken(final String token, final User user) {
        final VerificationToken verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }

    public User findUser(final String userEmail) {
        return Optional.of(userRepository.findUserByEmail(userEmail))
                .orElseThrow(UserNotFoundException::new);
    }

    private VerificationToken findVerificationToken(final String token) throws VerificationTokenNotFoundException,
                                                                               VerificationTokenExpiredException {
        final VerificationToken verificationToken = tokenRepository.findVerificationTokenByValue(token);

        if (verificationToken == null) {
            throw new VerificationTokenNotFoundException();
        } else if (verificationToken.isExpired()) {
            throw new VerificationTokenExpiredException();
        }

        return verificationToken;
    }

    private boolean userExists(final String email) {
        return userRepository.findUserByEmail(email) != null;
    }

}
