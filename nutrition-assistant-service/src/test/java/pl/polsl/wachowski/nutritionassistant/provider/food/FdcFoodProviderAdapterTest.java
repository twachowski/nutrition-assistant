package pl.polsl.wachowski.nutritionassistant.provider.food;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.polsl.wachowski.fdc.client.FdcClient;
import pl.polsl.wachowski.nutritionassistant.api.food.Food;
import pl.polsl.wachowski.nutritionassistant.api.food.FoodBasicData;
import pl.polsl.wachowski.nutritionassistant.client.AlwaysFailingFdcClient;
import pl.polsl.wachowski.nutritionassistant.client.MockFdcClient;
import pl.polsl.wachowski.nutritionassistant.exception.provider.FdcException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FdcFoodProviderAdapterTest {

    @Test
    @DisplayName("Should throw FdcException when FDC food search result is failure")
    void shouldThrowFdcExceptionWhenFdcFoodSearchResultIsFailure() {
        //given
        final FdcClient fdcClient = new AlwaysFailingFdcClient();
        final FdcFoodProviderAdapter provider = new FdcFoodProviderAdapter(fdcClient);
        final Executable executable = () -> provider.searchFoods("potato");

        //when

        //then
        assertThrows(FdcException.class, executable);
    }

    @Test
    @DisplayName("Should return set of foods")
    void shouldReturnSetOfFoods() {
        //given
        final FdcClient fdcClient = new MockFdcClient();
        final FdcFoodProviderAdapter provider = new FdcFoodProviderAdapter(fdcClient);

        //when
        final Set<FoodBasicData> foods = provider.searchFoods("potato");

        //then
        assertNotNull(foods);
    }

    @Test
    @DisplayName("Should throw FdcException when FDC food result is failure")
    void shouldThrowFdcExceptionWhenFdcFoodResultIsFailure() {
        //given
        final FdcClient fdcClient = new AlwaysFailingFdcClient();
        final FdcFoodProviderAdapter provider = new FdcFoodProviderAdapter(fdcClient);
        final Executable executable = () -> provider.getFood("12345");

        //when

        //then
        assertThrows(FdcException.class, executable);
    }

    @Test
    @DisplayName("Should return food")
    void shouldReturnFood() {
        //given
        final FdcClient fdcClient = new MockFdcClient();
        final FdcFoodProviderAdapter provider = new FdcFoodProviderAdapter(fdcClient);

        //when
        final Food food = provider.getFood("12345");

        //then
        assertNotNull(food);
    }

}