package pl.polsl.wachowski.nutritionassistant.client;

import pl.polsl.wachowski.fdc.client.FdcClient;
import pl.polsl.wachowski.fdc.client.FdcResult;
import pl.polsl.wachowski.fdc.client.api.food.FdcFood;
import pl.polsl.wachowski.fdc.client.api.food.search.FdcFoodSearchResponse;

public class AlwaysFailingFdcClient implements FdcClient {

    @Override
    public FdcResult<FdcFoodSearchResponse> searchFoods(final String query) {
        return FdcResult.failure(new RuntimeException("Always fails"));
    }

    @Override
    public FdcResult<FdcFood> getFood(final long id) {
        return FdcResult.failure(new RuntimeException("Always fails"));
    }

}
