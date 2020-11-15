package pl.polsl.wachowski.nutritionassistant.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.polsl.wachowski.nutritionassistant.domain.repository.TokenRepository;
import pl.polsl.wachowski.nutritionassistant.scheduling.job.TokenCleanupJob;

@EnableScheduling
@Configuration
public class SchedulingConfig {

    @Bean
    @ConditionalOnProperty(name = "scheduling.token-cleanup.enabled", havingValue = "true")
    TokenCleanupJob tokenCleanupJob(final TokenRepository tokenRepository) {
        return new TokenCleanupJob(tokenRepository);
    }

}
