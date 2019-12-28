package pl.polsl.wachowski.nutritionassistant.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:mail.properties")
public class PropertiesConfig {
}
