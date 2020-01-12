package pl.polsl.wachowski.nutritionassistant.data.nutritionix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.data.FoodDataProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.dto.search.FoodSearchItemDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.nutritionix.NutritionixBrandedFoodItemDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.nutritionix.NutritionixCommonFoodItemDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.nutritionix.NutritionixSearchResultDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class NutritionixProviderAdapter implements FoodDataProviderAdapter {

    private final NutritionixProvider provider;

    @Autowired
    public NutritionixProviderAdapter(final NutritionixProvider provider) {
        this.provider = provider;
    }

    @Override
    public List<FoodSearchItemDTO> search(String query) {
        final NutritionixSearchResultDTO result = provider.search(query);
        final List<NutritionixCommonFoodItemDTO> commonFoods = result.getCommonFoods();
        final List<NutritionixBrandedFoodItemDTO> brandedFoods = result.getBrandedFoods();

        final List<FoodSearchItemDTO> foods = new ArrayList<>(commonFoods.size() + brandedFoods.size());
        commonFoods
                .stream()
                .map(NutritionixProviderAdapter::mapCommonFood)
                .forEach(foods::add);
        brandedFoods
                .stream()
                .map(NutritionixProviderAdapter::mapBrandedFood)
                .forEach(foods::add);

        return foods;
    }

    private static FoodSearchItemDTO mapCommonFood(final NutritionixCommonFoodItemDTO item) {
        return new FoodSearchItemDTO(
                item.getFoodName(),
                null,
                item.getFoodName(),
                NutritionDataProvider.NUTRITIONIX);
    }

    private static FoodSearchItemDTO mapBrandedFood(final NutritionixBrandedFoodItemDTO item) {
        return new FoodSearchItemDTO(
                item.getFoodName(),
                item.getBrandName(),
                item.getBrandId(),
                NutritionDataProvider.NUTRITIONIX);
    }

}
