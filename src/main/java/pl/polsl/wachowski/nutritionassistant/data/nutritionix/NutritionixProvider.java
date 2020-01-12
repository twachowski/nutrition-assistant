package pl.polsl.wachowski.nutritionassistant.data.nutritionix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.polsl.wachowski.nutritionassistant.config.api.NutritionixApiConfig;
import pl.polsl.wachowski.nutritionassistant.data.FoodDataProvider;
import pl.polsl.wachowski.nutritionassistant.dto.search.nutritionix.NutritionixSearchResultDTO;

@Component
public class NutritionixProvider implements FoodDataProvider<NutritionixSearchResultDTO> {

    private static final String APP_ID_HEADER_NAME = "x-app-id";
    private static final String API_KEY_HEADER_NAME = "x-app-key";

    private final NutritionixApiConfig config;

    private final RestTemplate restTemplate;

    private final HttpHeaders headers;

    @Autowired
    public NutritionixProvider(final NutritionixApiConfig config,
                               final RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
        this.headers = createHeaders();
    }

    @Override
    public NutritionixSearchResultDTO search(final String query) {
        final String url = String.format(config.getInstantUrl(), query);
        final HttpEntity request = new HttpEntity<>(null, headers);
        final ResponseEntity<NutritionixSearchResultDTO> result =
                restTemplate.exchange(url, HttpMethod.GET, request, NutritionixSearchResultDTO.class);
        return result.getBody();
    }

    private HttpHeaders createHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.set(APP_ID_HEADER_NAME, config.getAppId());
        headers.set(API_KEY_HEADER_NAME, config.getApiKey());
        return headers;
    }

}
