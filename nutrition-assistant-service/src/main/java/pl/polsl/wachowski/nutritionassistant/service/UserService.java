package pl.polsl.wachowski.nutritionassistant.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserBiometricsEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserCredentialsEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.VerificationTokenEntity;
import pl.polsl.wachowski.nutritionassistant.exception.user.UserAlreadyActiveException;
import pl.polsl.wachowski.nutritionassistant.exception.user.UserExistsException;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenException;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenExpiredException;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenNotFoundException;
import pl.polsl.wachowski.nutritionassistant.domain.repository.TokenRepository;
import pl.polsl.wachowski.nutritionassistant.domain.repository.UserRepository;

@Slf4j
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
            log.warn("User {} already exists", email);
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
    public void activateUser(final String token) throws VerificationTokenException,
                                                        UserAlreadyActiveException {
        final VerificationTokenEntity verificationToken = findVerificationToken(token);
        final UserEntity user = verificationToken.getUser();
        if (user.isActive()) {
            log.error("Activation failed - user {} is already active", user.getEmail());
            throw new UserAlreadyActiveException(user.getEmail());
        }

        user.activate();
    }

    public void createVerificationToken(final String token, final UserEntity user) {
        final VerificationTokenEntity verificationToken = new VerificationTokenEntity(token, user);
        tokenRepository.save(verificationToken);
    }

    private VerificationTokenEntity findVerificationToken(final String token) throws VerificationTokenException {
        final VerificationTokenEntity verificationToken = tokenRepository.findVerificationTokenByValue(token);

        if (verificationToken == null) {
            log.error("Token {} has not been found", token);
            throw new VerificationTokenNotFoundException();
        } else if (verificationToken.isExpired()) {
            log.error("Token {} has expired", token);
            throw new VerificationTokenExpiredException();
        }

        return verificationToken;
    }

    private boolean userExists(final String email) {
        return userRepository.findUserByEmail(email) != null;
    }

}
