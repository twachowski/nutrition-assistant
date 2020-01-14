package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.data.provider.FoodDataProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.dto.details.FoodDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.FoodSearchItemDTO;
import pl.polsl.wachowski.nutritionassistant.exception.provider.ProviderNotFoundException;

import java.util.LinkedList;
import java.util.List;

@Service
public class FoodService {

    private final List<FoodDataProviderAdapter> providers;

    @Autowired
    public FoodService(final List<FoodDataProviderAdapter> providers) {
        this.providers = providers;
    }

    public List<FoodSearchItemDTO> search(final String query) {
        final List<FoodSearchItemDTO> results = new LinkedList<>();
        providers.forEach(provider -> results.addAll(provider.search(query)));
        return results;
    }

    public FoodDetailsDTO getDetails(final String foodId, final NutritionDataProvider providerType) {
        final FoodDataProviderAdapter provider = providers
                .stream()
                .filter(p -> p.getProviderType().equals(providerType))
                .findFirst()
                .orElseThrow(() -> new ProviderNotFoundException(providerType));
        return provider.getDetails(foodId);
    }

}
