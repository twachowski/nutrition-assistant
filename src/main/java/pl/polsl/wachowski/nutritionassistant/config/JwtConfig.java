package pl.polsl.wachowski.nutritionassistant.config;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Date;

@Configuration
@PropertySource("classpath:jwt.properties")
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Getter
    private Algorithm algorithm;

    @Value("${jwt.expiration.time}")
    private Long expirationTime;

    @PostConstruct
    private void initialize() {
        this.algorithm = Algorithm.HMAC512(secret);
    }

    public Date getExpiryDate() {
        return Date.from(Instant.now().plusSeconds(expirationTime));
    }

}
