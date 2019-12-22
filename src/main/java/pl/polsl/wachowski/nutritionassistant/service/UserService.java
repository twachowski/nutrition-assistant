package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.db.user.User;
import pl.polsl.wachowski.nutritionassistant.db.user.UserCredentials;
import pl.polsl.wachowski.nutritionassistant.db.user.VerificationToken;
import pl.polsl.wachowski.nutritionassistant.exception.UserAlreadyActiveException;
import pl.polsl.wachowski.nutritionassistant.exception.UserExistsException;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenExpiredException;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenNotFoundException;
import pl.polsl.wachowski.nutritionassistant.repository.TokenRepository;
import pl.polsl.wachowski.nutritionassistant.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(
            final UserRepository userRepository,
            final TokenRepository tokenRepository,
            final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void createUser(final User user) throws UserExistsException {
        if (userExists(user.getEmail())) {
            throw new UserExistsException("Email already exists");
        }
        encodeUserPassword(user.getUserCredentials());
        userRepository.save(user);
    }

    public void activateUser(final String token) throws VerificationTokenNotFoundException,
                                                        VerificationTokenExpiredException,
                                                        UserAlreadyActiveException {
        final VerificationToken verificationToken = findVerificationToken(token);
        final User user = verificationToken.getUser();
        if (user.isActive()) {
            throw new UserAlreadyActiveException("Your account has been already activated");
        }
        user.activate();
        tokenRepository.delete(verificationToken);
    }

    public void createVerificationToken(final String token, final User user) {
        final VerificationToken verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
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
        final String encodedPassword = bCryptPasswordEncoder.encode(userCredentials.getPassword());
        userCredentials.setPassword(encodedPassword);
    }

}
