package pl.polsl.wachowski.nutritionassistant.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.exception.user.InvalidCredentialsException;
import pl.polsl.wachowski.nutritionassistant.exception.user.UserInactiveException;
import pl.polsl.wachowski.nutritionassistant.repository.UserRepository;

@Slf4j
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationManager(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String userEmail = authentication.getPrincipal().toString();
        final String password = authentication.getCredentials().toString();

        final UserEntity user = userRepository.findUserByEmail(userEmail);
        if (user == null || credentialsDiffer(password, user.getUserCredentials().getPassword())) {
            log.info("Failed to authenticate user {} - {}",
                     userEmail,
                     user == null ? "user not found" : "bad credentials");
            throw new InvalidCredentialsException();
        } else if (!user.isActive()) {
            log.info("Failed to authenticate user {} - user has not been activated", userEmail);
            throw new UserInactiveException();
        }

        return new UsernamePasswordAuthenticationToken(userEmail, password);
    }

    private boolean credentialsDiffer(final String password, final String encodedPassword) {
        return !passwordEncoder.matches(password, encodedPassword);
    }

}
