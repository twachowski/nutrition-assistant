package pl.polsl.wachowski.nutritionassistant.scheduling.job.token.cleanup;

import lombok.extern.slf4j.Slf4j;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.repository.TokenRepository;

@Slf4j
public class AssignedTokensCleanupTask implements TokenCleanupTask {

    private final TokenRepository tokenRepository;

    public AssignedTokensCleanupTask(final TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void execute() {
        final int deletedRows = tokenRepository.deleteVerificationTokensByUserStatus(UserEntity.UserStatus.ACTIVE);
        if (deletedRows > 0) {
            log.info("Deleted {} verification tokens assigned to active users", deletedRows);
        } else {
            log.info("No verification tokens assigned to active users have been found");
        }
    }

}
