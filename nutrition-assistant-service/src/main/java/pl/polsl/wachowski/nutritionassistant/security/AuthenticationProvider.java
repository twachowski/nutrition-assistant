package pl.polsl.wachowski.nutritionassistant.security;

import org.springframework.security.core.Authentication;

public interface AuthenticationProvider {

    Authentication getAuthentication();

}
