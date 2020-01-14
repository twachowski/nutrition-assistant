package pl.polsl.wachowski.nutritionassistant.data.fdc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.polsl.wachowski.nutritionassistant.config.api.FdcApiConfig;
import pl.polsl.wachowski.nutritionassistant.data.FoodDataProvider;
import pl.polsl.wachowski.nutritionassistant.dto.details.fdc.FdcFoodDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.fdc.FdcSearchRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.fdc.FdcSearchResultDTO;

@Component
public class FdcProvider implements FoodDataProvider<FdcSearchResultDTO, FdcFoodDetailsDTO> {

    private final FdcApiConfig config;

    private final RestTemplate restTemplate;

    @Autowired
    public FdcProvider(final FdcApiConfig config,
                       final RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
    }

    @Override
    public FdcSearchResultDTO search(final String query) {
        final String url = String.format(config.getFoodSearchUrl(), config.getApiKey());
        final FdcSearchRequestDTO request = new FdcSearchRequestDTO(query);
        return restTemplate.postForObject(url, request, FdcSearchResultDTO.class);
    }

    @Override
    public FdcFoodDetailsDTO getDetails(final String id) {
        final String url = String.format(config.getFoodDetailsUrl(), id, config.getApiKey());
        return restTemplate.getForObject(url, FdcFoodDetailsDTO.class);
    }

}
