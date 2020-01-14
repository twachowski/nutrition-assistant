package pl.polsl.wachowski.nutritionassistant.data.provider.nutritionix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.polsl.wachowski.nutritionassistant.config.api.NutritionixApiConfig;
import pl.polsl.wachowski.nutritionassistant.data.provider.FoodDataProvider;
import pl.polsl.wachowski.nutritionassistant.dto.details.nutritionix.NutritionixFoodDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.details.nutritionix.NutritionixFoodDetailsRequestBodyDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.nutritionix.NutritionixSearchResultDTO;

@Component
public class NutritionixProvider implements FoodDataProvider<NutritionixSearchResultDTO, NutritionixFoodDetailsDTO> {

    private static final String SERVING_100G = "100g";

    private final NutritionixApiConfig config;

    private final RestTemplate restTemplate;

    @Autowired
    public NutritionixProvider(final NutritionixApiConfig config, final RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
    }

    @Override
    public NutritionixSearchResultDTO search(final String query) {
        final String url = String.format(config.getInstantUrl(), query);
        final HttpEntity request = new HttpEntity<>(null, config.getHeaders());
        final ResponseEntity<NutritionixSearchResultDTO> result =
                restTemplate.exchange(url, HttpMethod.GET, request, NutritionixSearchResultDTO.class);
        return result.getBody();
    }

    @Override
    public NutritionixFoodDetailsDTO getDetails(final String id) {
        final String url = config.getNutrientsUrl();
        final String query = String.join(" ", id, SERVING_100G);
        final NutritionixFoodDetailsRequestBodyDTO body = new NutritionixFoodDetailsRequestBodyDTO(query);
        final HttpEntity<NutritionixFoodDetailsRequestBodyDTO> request = new HttpEntity<>(body, config.getHeaders());
        return restTemplate.postForObject(url, request, NutritionixFoodDetailsDTO.class);
    }

}
