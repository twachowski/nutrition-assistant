package pl.polsl.wachowski.fdc.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

import java.time.Duration;
import java.util.Objects;

public class FdcClientFactory {

    private final FdcClientConfig config;
    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    public FdcClientFactory(final FdcClientConfig config,
                            final OkHttpClient okHttpClient,
                            final ObjectMapper objectMapper) {
        this.config = Objects.requireNonNull(config);
        this.okHttpClient = customizeOkHttpClient(Objects.requireNonNull(okHttpClient));
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    public FdcClient create() {
        return new RealFdcClient(okHttpClient, objectMapper, config);
    }

    private OkHttpClient customizeOkHttpClient(final OkHttpClient okHttpClient) {
        return okHttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(config.getConnectTimeout()))
                .writeTimeout(Duration.ofMillis(config.getWriteTimeout()))
                .readTimeout(Duration.ofMillis(config.getReadTimeout()))
                .build();
    }

}
