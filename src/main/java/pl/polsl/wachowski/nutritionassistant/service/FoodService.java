package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.data.provider.FoodDataProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.db.entry.FoodEntry;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.dto.details.FoodDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.food.EditedFoodEntryDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.FoodSearchItemDTO;
import pl.polsl.wachowski.nutritionassistant.exception.entry.EntryNotFoundException;
import pl.polsl.wachowski.nutritionassistant.exception.provider.ProviderNotFoundException;
import pl.polsl.wachowski.nutritionassistant.repository.FoodRepository;

import java.util.LinkedList;
import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    private final List<FoodDataProviderAdapter> providers;

    @Autowired
    public FoodService(final FoodRepository foodRepository,
                       final List<FoodDataProviderAdapter> providers) {
        this.foodRepository = foodRepository;
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

    public void editFoodEntry(final List<FoodEntry> foodEntries, final EditedFoodEntryDTO editedEntry) {
        final FoodEntry foodEntry = foodEntries
                .stream()
                .filter(entry -> entry.getPosition().equals(editedEntry.getPosition()))
                .findFirst()
                .orElseThrow(EntryNotFoundException::new);
        foodEntry.setAmount(editedEntry.getAmount());
        foodEntry.setUnit(editedEntry.getUnit());

        foodRepository.save(foodEntry);
    }

}
