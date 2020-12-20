package pl.polsl.wachowski.nutritionassistant.scheduling.job.token.cleanup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
public class TokenCleanupJob {

    private final List<TokenCleanupTask> cleanupTasks;

    public TokenCleanupJob(final List<TokenCleanupTask> cleanupTasks) {
        this.cleanupTasks = cleanupTasks;
    }

    @Scheduled(cron = "${scheduling.token-cleanup.cron}")
    @Transactional
    public void run() {
        log.debug("Performing verification token cleanup...");
        cleanupTasks.forEach(TokenCleanupTask::execute);
        log.debug("Token cleanup has finished");
    }

}
