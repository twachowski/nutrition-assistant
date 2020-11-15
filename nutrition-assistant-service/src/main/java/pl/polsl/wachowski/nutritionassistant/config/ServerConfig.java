package pl.polsl.wachowski.nutritionassistant.config;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static pl.polsl.wachowski.nutritionassistant.api.NutritionAssistantApi.API_PREFIX;

@Configuration
public class ServerConfig {

    @Bean
    WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.setContextPath(API_PREFIX);
    }

}
