package pl.polsl.wachowski.nutritionassistant.scheduling.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.repository.TokenRepository;

@Slf4j
public class TokenCleanupJob {

    private final TokenRepository tokenRepository;

    public TokenCleanupJob(final TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Scheduled(cron = "${scheduling.token-cleanup.cron}")
    @Transactional
    public void run() {
        log.debug("Performing verification token cleanup...");
        deleteTokensAssignedToActiveUsers();
        deleteExpiredTokens();
        log.debug("Token cleanup has finished");
    }

    private void deleteTokensAssignedToActiveUsers() {
        final int deletedRows = tokenRepository.deleteVerificationTokensByUserStatus(UserEntity.UserStatus.ACTIVE.ordinal());
        if (deletedRows > 0) {
            log.info("Deleted {} verification tokens assigned to active users", deletedRows);
        } else {
            log.info("No verification tokens assigned to active users have been found");
        }
    }

    private void deleteExpiredTokens() {
        final int deletedRows = tokenRepository.deleteExpiredVerificationTokens();
        if (deletedRows > 0) {
            log.info("Deleted {} expired verification tokens", deletedRows);
        } else {
            log.info("No expired verification tokens have been found");
        }
    }

}
