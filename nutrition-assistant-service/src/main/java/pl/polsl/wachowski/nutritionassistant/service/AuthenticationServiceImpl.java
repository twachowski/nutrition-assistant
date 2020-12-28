package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.security.AuthenticationProvider;
import pl.polsl.wachowski.nutritionassistant.security.JwtHelper;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationProvider authenticationProvider;
    private final JwtHelper jwtHelper;

    @Autowired
    public AuthenticationServiceImpl(final AuthenticationManager authenticationManager,
                                     final AuthenticationProvider authenticationProvider,
                                     final JwtHelper jwtHelper) {
        this.authenticationManager = authenticationManager;
        this.authenticationProvider = authenticationProvider;
        this.jwtHelper = jwtHelper;
    }

    public String authenticate(final String userEmail, final String password) {
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEmail,
                                                                                                                password);
        authenticationManager.authenticate(authenticationToken);
        return jwtHelper.generateToken(userEmail);
    }

    public String getAuthenticatedUserEmail() {
        return authenticationProvider.getAuthentication().getName();
    }

}
