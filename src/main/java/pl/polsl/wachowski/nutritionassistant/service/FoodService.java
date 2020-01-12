package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.data.FoodDataProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.dto.search.FoodSearchItemDTO;

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

}
