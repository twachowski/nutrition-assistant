package pl.polsl.wachowski.nutritionassistant.client;

import pl.polsl.wachowski.fdc.client.FdcClient;
import pl.polsl.wachowski.fdc.client.FdcResult;
import pl.polsl.wachowski.fdc.client.api.food.FdcFood;
import pl.polsl.wachowski.fdc.client.api.food.search.FdcFoodSearchResponse;

import java.util.Collections;

public class MockFdcClient implements FdcClient {

    @Override
    public FdcResult<FdcFoodSearchResponse> searchFoods(final String query) {
        final FdcFoodSearchResponse response = new FdcFoodSearchResponse(0,
                                                                         1,
                                                                         1,
                                                                         Collections.emptySet());
        return FdcResult.success(response);
    }

    @Override
    public FdcResult<FdcFood> getFood(final long id) {
        final FdcFood food = new FdcFood("mock food",
                                         "mock brand",
                                         Collections.emptySet());
        return FdcResult.success(food);
    }

}
