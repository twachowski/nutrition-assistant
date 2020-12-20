package pl.polsl.wachowski.nutritionassistant.scheduling.job.token.cleanup;

import lombok.extern.slf4j.Slf4j;
import pl.polsl.wachowski.nutritionassistant.domain.repository.TokenRepository;

@Slf4j
public class ExpiredTokensCleanupTask implements TokenCleanupTask {

    private final TokenRepository tokenRepository;

    public ExpiredTokensCleanupTask(final TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void execute() {
        final int deletedRows = tokenRepository.deleteExpiredVerificationTokens();
        if (deletedRows > 0) {
            log.info("Deleted {} expired verification tokens", deletedRows);
        } else {
            log.info("No expired verification tokens have been found");
        }
    }

}

