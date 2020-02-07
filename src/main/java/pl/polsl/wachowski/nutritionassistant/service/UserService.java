package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.wachowski.nutritionassistant.db.user.User;
import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometrics;
import pl.polsl.wachowski.nutritionassistant.db.user.UserCredentials;
import pl.polsl.wachowski.nutritionassistant.db.user.VerificationToken;
import pl.polsl.wachowski.nutritionassistant.dto.user.UserBiometricsDTO;
import pl.polsl.wachowski.nutritionassistant.exception.*;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenExpiredException;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenNotFoundException;
import pl.polsl.wachowski.nutritionassistant.repository.TokenRepository;
import pl.polsl.wachowski.nutritionassistant.repository.UserRepository;
import pl.polsl.wachowski.nutritionassistant.security.AuthenticationProvider;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public UserService(final UserRepository userRepository,
                       final TokenRepository tokenRepository,
                       final PasswordEncoder passwordEncoder,
                       final AuthenticationProvider authenticationProvider) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
    }

    public void createUser(final User user) throws UserExistsException {
        if (userExists(user.getEmail())) {
            throw new UserExistsException("Email already exists");
        }
        encodeUserPassword(user.getUserCredentials());
        userRepository.save(user);
    }

    @Transactional
    public void activateUser(final String token) throws VerificationTokenNotFoundException,
                                                        VerificationTokenExpiredException,
                                                        UserAlreadyActiveException {
        final VerificationToken verificationToken = findVerificationToken(token);
        final User user = verificationToken.getUser();
        if (user.isActive()) {
            throw new UserAlreadyActiveException("Your account has been already activated");
        }
        user.activate();
        tokenRepository.deleteAllByUser(user);
    }

    public void createVerificationToken(final String token, final User user) {
        final VerificationToken verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }

    public UserBiometrics getUserBiometrics(final String userEmail) {
        return findUserFetchBiometrics(userEmail).getUserBiometrics();
    }

    public UserBiometricsDTO getUserBiometricsDTO() {
        final String user = authenticationProvider.getAuthentication().getName();
        final UserBiometrics userBiometrics = getUserBiometrics(user);
        return new UserBiometricsDTO(
                userBiometrics.getDateOfBirth(),
                userBiometrics.getSex(),
                userBiometrics.getHeight(),
                userBiometrics.getWeight(),
                userBiometrics.getActivityLevel(),
                userBiometrics.getCalorieGoal());
    }

    public void saveUserBiometrics(final UserBiometricsDTO biometrics) {
        final String userEmail = authenticationProvider.getAuthentication().getName();
        final User user = findUserFetchBiometrics(userEmail);
        final UserBiometrics userBiometrics = user.getUserBiometrics();
        userBiometrics.setDateOfBirth(biometrics.getDateOfBirth());
        userBiometrics.setSex(biometrics.getSex());
        userBiometrics.setHeight(biometrics.getHeight());
        userBiometrics.setWeight(biometrics.getWeight());
        userBiometrics.setActivityLevel(biometrics.getActivityLevel());
        userBiometrics.setCalorieGoal(biometrics.getCalorieGoal());

        userRepository.save(user);
    }

    public User findUser(final String userEmail) {
        return Optional.of(userRepository.findUserByEmail(userEmail))
                .orElseThrow(UserNotFoundException::new);
    }

    private User findUserFetchBiometrics(final String userEmail) {
        return Optional.of(userRepository.findUserByEmailFetchBiometrics(userEmail))
                .orElseThrow(UserNotFoundException::new);
    }

    private VerificationToken findVerificationToken(final String token) throws  VerificationTokenNotFoundException,
                                                                                VerificationTokenExpiredException {
        final VerificationToken verificationToken = tokenRepository.findVerificationTokenByValue(token);

        if (verificationToken == null) {
            throw new VerificationTokenNotFoundException("Invalid verification token");
        } else if (verificationToken.isExpired()) {
            throw new VerificationTokenExpiredException("This token has expired");
        }

        return verificationToken;
    }

    private boolean userExists(final String email) {
        return userRepository.findUserByEmail(email) != null;
    }

    private void encodeUserPassword(final UserCredentials userCredentials) {
        final String encodedPassword = passwordEncoder.encode(userCredentials.getPassword());
        userCredentials.setPassword(encodedPassword);
    }

}
