package pl.polsl.wachowski.nutritionassistant.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.config.JwtConfig;

import java.util.Date;

@Component
public class JwtHelper {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final JwtConfig jwtConfig;

    @Autowired
    public JwtHelper(final JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        this.algorithm = jwtConfig.getAlgorithm();
        this.verifier = JWT.require(algorithm).build();
    }

    public String generateToken(final String subject) {
        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(new Date())
                .withExpiresAt(jwtConfig.getExpiryDate())
                .sign(algorithm);
    }

    public boolean isValid(final String token) {
        try {
            verifier.verify(token);
            return true;
        } catch (final JWTVerificationException ex) {
            return false;
        }
    }

    public static String extractUserFrom(final String token) {
        return JWT.decode(token)
                .getSubject();
    }

}
