package pl.polsl.wachowski.nutritionassistant.facade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.polsl.wachowski.fdc.client.FdcClient;
import pl.polsl.wachowski.nutritionassistant.client.AlwaysFailingFdcClient;
import pl.polsl.wachowski.nutritionassistant.client.AlwaysFailingNutritionixClient;
import pl.polsl.wachowski.nutritionassistant.client.MockFdcClient;
import pl.polsl.wachowski.nutritionassistant.client.MockNutritionixClient;
import pl.polsl.wachowski.nutritionassistant.exception.provider.FdcException;
import pl.polsl.wachowski.nutritionassistant.exception.provider.NutritionixException;
import pl.polsl.wachowski.nutritionassistant.service.FoodService;
import pl.polsl.wachowski.nutritionassistant.samples.FoodServiceSamples;
import pl.polsl.wachowski.nutritionix.client.NutritionixClient;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FoodFacadeTest {

    @Test
    @DisplayName("Should throw FdcException when FDC food search fails")
    void shouldThrowFdcExceptionWhenFdcFoodSearchFails() {
        //given
        final FdcClient fdcClient = new AlwaysFailingFdcClient();
        final NutritionixClient nutritionixClient = new MockNutritionixClient();
        final FoodService foodService = FoodServiceSamples.withMockFoodRepository(fdcClient, nutritionixClient);
        final FoodFacade foodFacade = new FoodFacade(foodService);
        final Executable executable = () -> foodFacade.searchFoods("potato");

        //when

        //then
        assertThrows(FdcException.class, executable);
    }

    @Test
    @DisplayName("Should throw NutritionixException when Nutritionix food search fails")
    void shouldThrowNutritionixExceptionWhenNutritionixFoodSearchFails() {
        //given
        final FdcClient fdcClient = new MockFdcClient();
        final NutritionixClient nutritionixClient = new AlwaysFailingNutritionixClient();
        final FoodService foodService = FoodServiceSamples.withMockFoodRepository(fdcClient, nutritionixClient);
        final FoodFacade foodFacade = new FoodFacade(foodService);
        final Executable executable = () -> foodFacade.searchFoods("potato");

        //when

        //then
        assertThrows(NutritionixException.class, executable);
    }

}