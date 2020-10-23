package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.EditedFoodEntry;
import pl.polsl.wachowski.nutritionassistant.api.food.Food;
import pl.polsl.wachowski.nutritionassistant.api.food.FoodBasicData;
import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.data.provider.FoodDataProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.db.entry.FoodEntryEntity;
import pl.polsl.wachowski.nutritionassistant.exception.entry.EntryNotFoundException;
import pl.polsl.wachowski.nutritionassistant.exception.provider.ProviderNotFoundException;
import pl.polsl.wachowski.nutritionassistant.repository.FoodRepository;
import pl.polsl.wachowski.nutritionassistant.util.NutrientHelper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Set<FoodBasicData> searchFoods(final String query) {
        return providers.stream()
                .map(provider -> provider.search(query))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public Food getFood(final String id, final NutritionDataProvider provider) {
        final FoodDataProviderAdapter providerAdapter = providers.stream()
                .filter(p -> p.getProviderType().equals(provider))
                .findFirst()
                .orElseThrow(() -> new ProviderNotFoundException(provider));

        final Food food = providerAdapter.getFood(id);
        NutrientHelper.addMandatoryNutrientsIfMissing(food.getNutrients());

        return food;
    }

    public void editFoodEntry(final List<FoodEntryEntity> foodEntries,
                              final short entryPosition,
                              final EditedFoodEntry editedFoodEntry) {
        final FoodEntryEntity foodEntry = foodEntries.stream()
                .filter(entry -> entry.getPosition().equals(entryPosition))
                .findFirst()
                .orElseThrow(EntryNotFoundException::new);
        foodEntry.setAmount(editedFoodEntry.getAmount());
        foodEntry.setUnit(editedFoodEntry.getMassUnit());
        foodRepository.save(foodEntry);
    }

}
