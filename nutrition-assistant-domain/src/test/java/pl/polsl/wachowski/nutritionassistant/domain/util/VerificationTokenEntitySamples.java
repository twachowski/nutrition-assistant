package pl.polsl.wachowski.nutritionassistant.domain.util;

import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.VerificationTokenEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class VerificationTokenEntitySamples {

    private VerificationTokenEntitySamples() {
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

    public static List<VerificationTokenEntity> randomTokens(final UserEntity userEntity) {
        return Stream.of(randomActiveTokens(userEntity), randomExpiredTokens(userEntity))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

}
