package pl.polsl.wachowski.nutritionassistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.polsl.wachowski.nutritionassistant.fdc.client.FdcClientConfig;

@SpringBootApplication
@EnableConfigurationProperties(value = {
        FdcClientConfig.class
})
public class NutritionAssistantApplication {

    public static void main(final String[] args) {
        SpringApplication.run(NutritionAssistantApplication.class, args);
    }

}
