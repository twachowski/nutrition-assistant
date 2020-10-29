package pl.polsl.wachowski.nutritionix.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

public class NutritionixClientFactory {

    private final NutritionixClientConfig config;
    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    public NutritionixClientFactory(final NutritionixClientConfig config,
                                    final OkHttpClient okHttpClient,
                                    final ObjectMapper objectMapper) {
        this.config = Objects.requireNonNull(config);
        this.okHttpClient = Optional.ofNullable(okHttpClient)
                .orElseGet(() -> defaultOkHttpClient(config.getConnectTimeout(), config.getReadTimeout()));
        this.objectMapper = Optional.ofNullable(objectMapper)
                .orElseGet(ObjectMapper::new);
    }

    public NutritionixClient create() {
        return new RealNutritionixClient(okHttpClient, objectMapper, config);
    }

    private static OkHttpClient defaultOkHttpClient(final long connectTimeout, final long readTimeout) {
        return new OkHttpClient.Builder()
                .connectTimeout(Duration.ofMillis(connectTimeout))
                .readTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

}
