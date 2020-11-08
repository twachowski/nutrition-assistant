package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.wachowski.nutritionassistant.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometricsEntity;
import pl.polsl.wachowski.nutritionassistant.db.user.UserCredentialsEntity;
import pl.polsl.wachowski.nutritionassistant.db.user.VerificationTokenEntity;
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

    public UserEntity addUser(final String email, final String password) throws UserExistsException {
        if (userExists(email)) {
            throw new UserExistsException("User with given email already exists");
        }
        final UserEntity user = new UserEntity(email);

        final String encodedPassword = passwordEncoder.encode(password);
        final UserCredentialsEntity userCredentials = new UserCredentialsEntity(encodedPassword, user);
        user.setUserCredentials(userCredentials);

        final UserBiometricsEntity userBiometrics = UserBiometricsEntity.getDefault(user);
        user.setUserBiometrics(userBiometrics);

        return userRepository.save(user);
    }

    @Transactional
    public void activateUser(final String token) throws VerificationTokenNotFoundException,
                                                        VerificationTokenExpiredException,
                                                        UserAlreadyActiveException {
        final VerificationTokenEntity verificationToken = findVerificationToken(token);
        final UserEntity user = verificationToken.getUser();
        if (user.isActive()) {
            throw new UserAlreadyActiveException();
        }

        user.activate();
        tokenRepository.deleteAllByUser(user);  //TODO do it asynchronously with event publisher
    }

    public void createVerificationToken(final String token, final UserEntity user) {
        final VerificationTokenEntity verificationToken = new VerificationTokenEntity(token, user);
        tokenRepository.save(verificationToken);
    }

    public UserEntity findUser(final String userEmail) {
        return Optional.of(userRepository.findUserByEmail(userEmail))
                .orElseThrow(UserNotFoundException::new);
    }

    private VerificationTokenEntity findVerificationToken(final String token) throws VerificationTokenNotFoundException,
                                                                                     VerificationTokenExpiredException {
        final VerificationTokenEntity verificationToken = tokenRepository.findVerificationTokenByValue(token);

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
