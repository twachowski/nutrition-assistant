package pl.polsl.wachowski.nutritionassistant.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.PBEConfig;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:jasypt.properties")
public class JasyptConfig {

    @Bean
    @ConfigurationProperties("jasypt.encryptor")
    public PBEConfig encryptorConfig() {
        return new SimpleStringPBEConfig();
    }

    @Bean
    public StringEncryptor jasyptEncryptor() {
        final PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(encryptorConfig());
        return encryptor;
    }

}
