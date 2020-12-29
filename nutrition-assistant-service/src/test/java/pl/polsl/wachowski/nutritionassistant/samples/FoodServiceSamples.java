package pl.polsl.wachowski.nutritionassistant.samples;

import org.mockito.Mockito;
import pl.polsl.wachowski.fdc.client.FdcClient;
import pl.polsl.wachowski.nutritionassistant.domain.repository.FoodRepository;
import pl.polsl.wachowski.nutritionassistant.provider.food.FdcFoodProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.provider.food.FoodProvider;
import pl.polsl.wachowski.nutritionassistant.provider.food.NutritionixFoodProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.service.FoodService;
import pl.polsl.wachowski.nutritionix.client.NutritionixClient;

import java.util.Arrays;

public final class FoodServiceSamples {

    private FoodServiceSamples() {
    }

    public static FoodService withMockFoodRepository(final FdcClient fdcClient,
                                                     final NutritionixClient nutritionixClient) {
        return foodService(Mockito.mock(FoodRepository.class),
                           fdcClient,
                           nutritionixClient);
    }

    public static FoodService foodService(final FoodRepository foodRepository,
                                          final FdcClient fdcClient,
                                          final NutritionixClient nutritionixClient) {
        final FoodProvider fdcFoodProvider = new FdcFoodProviderAdapter(fdcClient);
        final FoodProvider nutritionixFoodProvider = new NutritionixFoodProviderAdapter(nutritionixClient);
        return new FoodService(foodRepository,
                               Arrays.asList(fdcFoodProvider, nutritionixFoodProvider));
    }

}
