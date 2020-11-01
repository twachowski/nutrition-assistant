package pl.polsl.wachowski.fdc.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@ConfigurationProperties(prefix = "usda.fdc.client")
@ConstructorBinding
@Validated
@AllArgsConstructor
@Getter
public class FdcClientConfig {

    @NotBlank
    private final String apiKey;

    @NotNull
    @PositiveOrZero
    private final Long connectTimeout;

    @NotNull
    @PositiveOrZero
    private final Long writeTimeout;

    @NotNull
    @PositiveOrZero
    private final Long readTimeout;

}
