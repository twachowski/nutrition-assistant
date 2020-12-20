package pl.polsl.wachowski.nutritionassistant.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.polsl.wachowski.nutritionassistant.domain.repository.TokenRepository;
import pl.polsl.wachowski.nutritionassistant.scheduling.job.token.cleanup.AssignedTokensCleanupTask;
import pl.polsl.wachowski.nutritionassistant.scheduling.job.token.cleanup.ExpiredTokensCleanupTask;
import pl.polsl.wachowski.nutritionassistant.scheduling.job.token.cleanup.TokenCleanupJob;
import pl.polsl.wachowski.nutritionassistant.scheduling.job.token.cleanup.TokenCleanupTask;

import java.util.Arrays;
import java.util.List;

@EnableScheduling
@Configuration
public class SchedulingConfig {

    @Bean
    @ConditionalOnProperty(name = "scheduling.token-cleanup.enabled", havingValue = "true")
    TokenCleanupJob tokenCleanupJob(final TokenRepository tokenRepository) {
        final List<TokenCleanupTask> cleanupTasks = Arrays.asList(new AssignedTokensCleanupTask(tokenRepository),
                                                                  new ExpiredTokensCleanupTask(tokenRepository));
        return new TokenCleanupJob(cleanupTasks);
    }

}
