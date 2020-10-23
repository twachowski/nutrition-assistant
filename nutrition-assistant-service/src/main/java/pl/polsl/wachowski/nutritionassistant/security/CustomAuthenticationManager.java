package pl.polsl.wachowski.nutritionassistant.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.db.user.User;
import pl.polsl.wachowski.nutritionassistant.exception.InvalidCredentialsException;
import pl.polsl.wachowski.nutritionassistant.exception.UserInactiveException;
import pl.polsl.wachowski.nutritionassistant.repository.UserRepository;

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

        final User user = userRepository.findUserByEmail(userEmail);
        if (user == null || credentialsDiffer(password, user.getUserCredentials().getPassword())) {
            throw new InvalidCredentialsException();
        } else if (!user.isActive()) {
            throw new UserInactiveException();
        }

        return new UsernamePasswordAuthenticationToken(userEmail, password);
    }

    private boolean credentialsDiffer(final String password, final String encodedPassword) {
        return !passwordEncoder.matches(password, encodedPassword);
    }

}
