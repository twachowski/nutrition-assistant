package pl.polsl.wachowski.nutritionix.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@ConfigurationProperties(prefix = "nutritionix.client")
@ConstructorBinding
@Validated
@AllArgsConstructor
@Getter
public class NutritionixClientConfig {

    @NotBlank
    private final String appId;

    @NotBlank
    private final String appKey;

    @NotNull
    @PositiveOrZero
    private final Long connectTimeout;

    @NotNull
    @PositiveOrZero
    private final Long readTimeout;

}
