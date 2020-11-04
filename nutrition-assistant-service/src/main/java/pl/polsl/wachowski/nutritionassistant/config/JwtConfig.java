package pl.polsl.wachowski.nutritionassistant.config;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.Instant;
import java.util.Date;

@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
@Validated
@Getter
public class JwtConfig {

    @NotBlank
    private final String secret;

    @NotNull
    @PositiveOrZero
    private final Long expirationTime;

    private final Algorithm algorithm;

    public JwtConfig(final String secret, final Long expirationTime) {
        this.secret = secret;
        this.expirationTime = expirationTime;
        this.algorithm = Algorithm.HMAC512(secret);
    }

    public Date getExpiryDate() {
        return Date.from(Instant.now().plusSeconds(expirationTime));
    }

}
