package pl.polsl.wachowski.nutritionassistant.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoggedUserAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
