package pl.polsl.wachowski.nutritionassistant.scheduling.util;

import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.VerificationTokenEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public final class VerificationTokenSamples {

    private VerificationTokenSamples() {
    }

    public static List<VerificationTokenEntity> randomActiveTokens(final UserEntity userEntity) {
        final Function<UUID, VerificationTokenEntity> tokenFunction = uuid -> new VerificationTokenEntity(uuid.toString(),
                                                                                                          userEntity);
        return Arrays.asList(tokenFunction.apply(UUID.randomUUID()),
                             tokenFunction.apply(UUID.randomUUID()),
                             tokenFunction.apply(UUID.randomUUID()),
                             tokenFunction.apply(UUID.randomUUID()),
                             tokenFunction.apply(UUID.randomUUID()));
    }

    public static List<VerificationTokenEntity> randomExpiredTokens(final UserEntity userEntity) {
        final LocalDateTime yesterday = LocalDateTime.now().minusDays(1L);
        final Function<UUID, VerificationTokenEntity> tokenFunction = uuid -> new VerificationTokenEntity(uuid.toString(),
                                                                                                          yesterday,
                                                                                                          userEntity);
        return Arrays.asList(tokenFunction.apply(UUID.randomUUID()),
                             tokenFunction.apply(UUID.randomUUID()),
                             tokenFunction.apply(UUID.randomUUID()),
                             tokenFunction.apply(UUID.randomUUID()),
                             tokenFunction.apply(UUID.randomUUID()));
    }

}
