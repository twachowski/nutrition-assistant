package pl.polsl.wachowski.nutritionassistant.config.api;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource("classpath:nutrition-api.properties")
public class NutritionixApiConfig {

    @Value("${nutritionix-api.app-id}")
    private String appId;

    @Value("${nutritionix-api.key}")
    private String apiKey;

    @Getter
    @Value("${nutritionix-api.instant.url}")
    private String instantUrl;

    @Getter
    @Value("${nutritionix-api.nutrients.url}")
    private String nutrientsUrl;

    @Getter
    @Value("${nutritionix-api.exercise.url}")
    private String exerciseUrl;

    @Value("${nutritionix-api.app-id.header}")
    private String appIdHeaderName;

    @Value("${nutritionix-api.key.header}")
    private String apiKeyHeaderName;

    @Getter
    private HttpHeaders headers;

    @PostConstruct
    private void initializeHeaders() {
        headers = new HttpHeaders();
        headers.set(appIdHeaderName, appId);
        headers.set(apiKeyHeaderName, apiKey);
    }

}
