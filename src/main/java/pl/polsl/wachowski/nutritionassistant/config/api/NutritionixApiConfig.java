package pl.polsl.wachowski.nutritionassistant.config.api;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:nutrition-api.properties")
public class NutritionixApiConfig {

    @Value("${nutritionix-api.app-id}")
    private String appId;

    @Value("${nutritionix-api.key}")
    private String apiKey;

    @Value("${nutritionix-api.instant.url}")
    private String instantUrl;

    @Value("${nutritionix-api.nutrients.url}")
    private String nutrientsUrl;

}
