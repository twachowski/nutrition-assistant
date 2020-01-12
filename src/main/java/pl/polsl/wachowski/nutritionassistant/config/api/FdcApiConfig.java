package pl.polsl.wachowski.nutritionassistant.config.api;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:nutrition-api.properties")
public class FdcApiConfig {

    @Value("${usda.fdc-api.key}")
    private String apiKey;

    @Value("${usda.fdc-api.food-search.url}")
    private String foodSearchUrl;

    @Value("${usda.fdc-api.food-details.url}")
    private String foodDetailsUrl;

}
