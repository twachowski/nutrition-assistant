package pl.polsl.wachowski.nutritionassistant.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {

    @Bean
    @ConfigurationProperties("jasypt.encryptor")
    public SimpleStringPBEConfig jasyptConfig() {
        return new SimpleStringPBEConfig();
    }

    @Bean
    public StringEncryptor jasyptEncryptor() {
        final PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(jasyptConfig());
        return encryptor;
    }

}
