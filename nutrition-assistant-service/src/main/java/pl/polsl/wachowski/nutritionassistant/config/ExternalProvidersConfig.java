package pl.polsl.wachowski.nutritionassistant.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.polsl.wachowski.fdc.client.FdcClient;
import pl.polsl.wachowski.fdc.client.FdcClientConfig;
import pl.polsl.wachowski.fdc.client.FdcClientFactory;
import pl.polsl.wachowski.nutritionix.client.NutritionixClient;
import pl.polsl.wachowski.nutritionix.client.NutritionixClientConfig;
import pl.polsl.wachowski.nutritionix.client.NutritionixClientFactory;

import java.time.Duration;

@Configuration
public class ExternalProvidersConfig {

    @Bean
    OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(10))
                .writeTimeout(Duration.ofSeconds(10))
                .readTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Bean
    NutritionixClient nutritionixClient(final NutritionixClientConfig clientConfig,
                                        final OkHttpClient okHttpClient,
                                        final ObjectMapper objectMapper) {
        final NutritionixClientFactory factory = new NutritionixClientFactory(clientConfig,
                                                                              okHttpClient,
                                                                              objectMapper);
        return factory.create();
    }

    @Bean
    FdcClient fdcClient(final FdcClientConfig clientConfig,
                        final OkHttpClient okHttpClient,
                        final ObjectMapper objectMapper) {
        final FdcClientFactory factory = new FdcClientFactory(clientConfig,
                                                              okHttpClient,
                                                              objectMapper);
        return factory.create();
    }

}
