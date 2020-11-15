package pl.polsl.wachowski.nutritionassistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import pl.polsl.wachowski.fdc.client.FdcClientConfig;
import pl.polsl.wachowski.nutritionassistant.config.JwtConfig;
import pl.polsl.wachowski.nutritionix.client.NutritionixClientConfig;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties(value = {
        JwtConfig.class,
        FdcClientConfig.class,
        NutritionixClientConfig.class
})
public class NutritionAssistantApplication {

    public static void main(final String[] args) {
        SpringApplication.run(NutritionAssistantApplication.class, args);
    }

}
