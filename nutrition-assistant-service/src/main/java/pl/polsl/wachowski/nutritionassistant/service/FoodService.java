package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.EditedFoodEntry;
import pl.polsl.wachowski.nutritionassistant.api.food.Food;
import pl.polsl.wachowski.nutritionassistant.api.food.FoodBasicData;
import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.FoodEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.repository.FoodRepository;
import pl.polsl.wachowski.nutritionassistant.exception.entry.EntryNotFoundException;
import pl.polsl.wachowski.nutritionassistant.provider.food.FoodProvider;
import pl.polsl.wachowski.nutritionassistant.util.ConcurrentUtils;
import pl.polsl.wachowski.nutritionassistant.util.NutrientHelper;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class FoodService {

    private final FoodRepository foodRepository;
    private final List<FoodProvider> foodProviders;

    @Autowired
    public FoodService(final FoodRepository foodRepository,
                       final List<FoodProvider> foodProviders) {
        this.foodRepository = foodRepository;
        this.foodProviders = foodProviders;
    }

    public Set<FoodBasicData> searchFoods(final String query) {
        final List<CompletableFuture<Set<FoodBasicData>>> searchTasks = foodProviders.stream()
                .map(provider -> CompletableFuture.supplyAsync(() -> provider.searchFoods(query)))
                .collect(Collectors.toList());
        CompletableFuture.allOf(searchTasks.toArray(new CompletableFuture[0]))
                .join();
        return searchTasks.stream()
                .map(ConcurrentUtils::extractFuture)
                .flatMap(Set::stream)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Food getFood(final String id, final NutritionDataProvider provider) {
        final FoodProvider foodProvider = foodProviders.stream()
                .filter(p -> p.getType().equals(provider))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Provider '" + provider + "' has not been found"));

        final Food food = foodProvider.getFood(id);
        NutrientHelper.addMandatoryNutrientsIfMissing(food.getNutrients());

        return food;
    }

    public void editFoodEntry(final List<FoodEntryEntity> foodEntries,
                              final short entryPosition,
                              final EditedFoodEntry editedFoodEntry) {
        final FoodEntryEntity foodEntry = foodEntries.stream()
                .filter(entry -> entry.getPosition().equals(entryPosition))
                .findFirst()
                .orElseThrow(() -> new EntryNotFoundException("There is no food entry at position " + entryPosition));
        foodEntry.setAmount(editedFoodEntry.getAmount());
        foodEntry.setUnit(editedFoodEntry.getMassUnit());
        foodRepository.save(foodEntry);
    }

}
