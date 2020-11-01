package pl.polsl.wachowski.nutritionix.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

import java.time.Duration;
import java.util.Objects;

public class NutritionixClientFactory {

    private final NutritionixClientConfig config;
    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    public NutritionixClientFactory(final NutritionixClientConfig config,
                                    final OkHttpClient okHttpClient,
                                    final ObjectMapper objectMapper) {
        this.config = Objects.requireNonNull(config);
        this.okHttpClient = customizeOkHttpClient(Objects.requireNonNull(okHttpClient));
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    public NutritionixClient create() {
        return new RealNutritionixClient(okHttpClient, objectMapper, config);
    }

    private OkHttpClient customizeOkHttpClient(final OkHttpClient okHttpClient) {
        return okHttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(config.getConnectTimeout()))
                .readTimeout(Duration.ofMillis(config.getReadTimeout()))
                .build();
    }

}
