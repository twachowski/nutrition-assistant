package pl.polsl.wachowski.nutritionassistant.data;

import pl.polsl.wachowski.nutritionassistant.dto.search.FoodSearchItemDTO;

import java.util.List;

public interface FoodDataProviderAdapter {

    List<FoodSearchItemDTO> search(final String query);

}
