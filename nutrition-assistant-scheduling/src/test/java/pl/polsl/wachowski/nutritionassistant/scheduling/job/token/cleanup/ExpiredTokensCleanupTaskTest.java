package pl.polsl.wachowski.nutritionassistant.scheduling.job.token.cleanup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.VerificationTokenEntity;
import pl.polsl.wachowski.nutritionassistant.domain.repository.TokenRepository;
import pl.polsl.wachowski.nutritionassistant.domain.repository.UserRepository;
import pl.polsl.wachowski.nutritionassistant.scheduling.samples.UserSamples;
import pl.polsl.wachowski.nutritionassistant.scheduling.samples.VerificationTokenSamples;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ContextConfiguration(classes = {TokenRepository.class, UserRepository.class})
@EnableJpaRepositories(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.repository")
@EntityScan(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.db")
class ExpiredTokensCleanupTaskTest {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final ExpiredTokensCleanupTask expiredTokensCleanupTask;

    @Autowired
    public ExpiredTokensCleanupTaskTest(final TokenRepository tokenRepository,
                                        final UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.expiredTokensCleanupTask = new ExpiredTokensCleanupTask(tokenRepository);
    }

    @Test
    @DisplayName("Should delete expired tokens assigned to active users")
    void shouldDeleteExpiredTokensAssignedToActiveUsers() {
        //given
        final UserEntity activeUser = UserSamples.activeUser();
        final List<VerificationTokenEntity> expiredTokens = VerificationTokenSamples.randomExpiredTokens(activeUser);

        //when
        userRepository.save(activeUser);
        tokenRepository.saveAll(expiredTokens);
        expiredTokensCleanupTask.execute();
        final long tokenCountAfterCleanup = tokenRepository.count();

        //then
        assertEquals(0L, tokenCountAfterCleanup);
    }

    @Test
    @DisplayName("Should delete expired tokens assigned to inactive users")
    void shouldDeleteExpiredTokensAssignedToInactiveUsers() {
        //given
        final UserEntity inactiveUser = UserSamples.inactiveUser();
        final List<VerificationTokenEntity> expiredTokens = VerificationTokenSamples.randomExpiredTokens(inactiveUser);

        //when
        userRepository.save(inactiveUser);
        tokenRepository.saveAll(expiredTokens);
        expiredTokensCleanupTask.execute();
        final long tokenCountAfterCleanup = tokenRepository.count();

        //then
        assertEquals(0L, tokenCountAfterCleanup);
    }

    @Test
    @DisplayName("Should not delete active tokens assigned to active users")
    void shouldNotDeleteActiveTokensAssignedToActiveUsers() {
        //given
        final UserEntity activeUser = UserSamples.activeUser();
        final List<VerificationTokenEntity> activeTokens = VerificationTokenSamples.randomActiveTokens(activeUser);

        //when
        userRepository.save(activeUser);
        tokenRepository.saveAll(activeTokens);
        expiredTokensCleanupTask.execute();
        final long tokenCountAfterCleanup = tokenRepository.count();

        //then
        assertEquals(activeTokens.size(), tokenCountAfterCleanup);
    }

    @Test
    @DisplayName("Should not delete active tokens assigned to inactive users")
    void shouldNotDeleteActiveTokensAssignedToInactiveUsers() {
        //given
        final UserEntity inactiveUser = UserSamples.activeUser();
        final List<VerificationTokenEntity> activeTokens = VerificationTokenSamples.randomActiveTokens(inactiveUser);

        //when
        userRepository.save(inactiveUser);
        tokenRepository.saveAll(activeTokens);
        expiredTokensCleanupTask.execute();
        final long tokenCountAfterCleanup = tokenRepository.count();

        //then
        assertEquals(activeTokens.size(), tokenCountAfterCleanup);
    }

}