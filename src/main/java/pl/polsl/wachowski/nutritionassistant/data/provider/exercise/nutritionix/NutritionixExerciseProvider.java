package pl.polsl.wachowski.nutritionassistant.data.provider.exercise.nutritionix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.polsl.wachowski.nutritionassistant.config.api.NutritionixApiConfig;
import pl.polsl.wachowski.nutritionassistant.data.provider.exercise.ExerciseDataProvider;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.nutritionix.NutritionixExerciseSearchRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.nutritionix.NutritionixExerciseSearchResultDTO;

@Component
public class NutritionixExerciseProvider implements ExerciseDataProvider<NutritionixExerciseSearchResultDTO, NutritionixExerciseSearchRequestDTO> {

    private final NutritionixApiConfig config;

    private final RestTemplate restTemplate;

    @Autowired
    public NutritionixExerciseProvider(final NutritionixApiConfig config, final RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
    }

    @Override
    public NutritionixExerciseSearchResultDTO search(final NutritionixExerciseSearchRequestDTO request) {
        final String url = config.getExerciseUrl();
        final HttpEntity<NutritionixExerciseSearchRequestDTO> requestEntity = new HttpEntity<>(request, config.getHeaders());
        return restTemplate.postForObject(url, requestEntity, NutritionixExerciseSearchResultDTO.class);
    }

}
