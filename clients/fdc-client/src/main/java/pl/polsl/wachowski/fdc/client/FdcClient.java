package pl.polsl.wachowski.fdc.client;

import pl.polsl.wachowski.fdc.client.api.food.FdcFood;
import pl.polsl.wachowski.fdc.client.api.food.search.FdcFoodSearchResponse;

public interface FdcClient {

    FdcResult<FdcFoodSearchResponse> searchFoods(String query);

    FdcResult<FdcFood> getFood(long id);

}
