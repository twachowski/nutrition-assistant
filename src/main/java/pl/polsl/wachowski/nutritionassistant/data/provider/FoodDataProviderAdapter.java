package pl.polsl.wachowski.nutritionassistant.data.provider;

import pl.polsl.wachowski.nutritionassistant.def.nutrition.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.dto.details.FoodDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.FoodSearchItemDTO;

import java.util.List;

public interface FoodDataProviderAdapter {

    List<FoodSearchItemDTO> search(final String query);

    FoodDetailsDTO getDetails(final String id);

    NutritionDataProvider getProviderType();

}
