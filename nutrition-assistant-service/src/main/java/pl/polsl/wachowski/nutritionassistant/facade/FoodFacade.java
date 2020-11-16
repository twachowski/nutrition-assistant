package pl.polsl.wachowski.nutritionassistant.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.EditedFoodEntry;
import pl.polsl.wachowski.nutritionassistant.api.food.Food;
import pl.polsl.wachowski.nutritionassistant.api.food.FoodBasicData;
import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.FoodEntryEntity;
import pl.polsl.wachowski.nutritionassistant.exception.provider.FdcException;
import pl.polsl.wachowski.nutritionassistant.exception.provider.NutritionixException;
import pl.polsl.wachowski.nutritionassistant.service.FoodService;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletionException;

@Component
public class FoodFacade {

    private final FoodService foodService;

    @Autowired
    public FoodFacade(final FoodService foodService) {
        this.foodService = foodService;
    }

    public Set<FoodBasicData> searchFoods(final String query) {
        try {
            return foodService.searchFoods(query);
        } catch (final CompletionException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof FdcException) {
                throw (FdcException) cause;
            }
            if (cause instanceof NutritionixException) {
                throw (NutritionixException) cause;
            }
            throw e;
        }
    }

    public Food getFood(final String id, final NutritionDataProvider provider) {
        return foodService.getFood(id, provider);
    }

    public void editFoodEntry(final List<FoodEntryEntity> foodEntries,
                              final short entryPosition,
                              final EditedFoodEntry editedFoodEntry) {
        foodService.editFoodEntry(foodEntries, entryPosition, editedFoodEntry);
    }

}
