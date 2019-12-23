package pl.polsl.wachowski.nutritionassistant.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.db.user.User;
import pl.polsl.wachowski.nutritionassistant.repository.UserRepository;

@Primary
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String userEmail) throws UsernameNotFoundException {
        final User user = userRepository.findUserByEmail(userEmail);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User email '%s' not found", userEmail));
        }

        return new UserPrincipal(user);
    }

}
