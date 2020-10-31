package pl.polsl.wachowski.nutritionassistant.fdc.client;

import pl.polsl.wachowski.nutritionassistant.fdc.client.api.food.FdcFood;
import pl.polsl.wachowski.nutritionassistant.fdc.client.api.food.search.FdcFoodSearchResponse;

public interface FdcClient {

    FdcResult<FdcFoodSearchResponse> searchFoods(String query);

    FdcResult<FdcFood> getFood(long id);

}
