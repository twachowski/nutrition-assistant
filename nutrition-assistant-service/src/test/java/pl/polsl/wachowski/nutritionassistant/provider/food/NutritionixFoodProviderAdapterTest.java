package pl.polsl.wachowski.nutritionassistant.provider.food;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.polsl.wachowski.nutritionassistant.api.food.Food;
import pl.polsl.wachowski.nutritionassistant.api.food.FoodBasicData;
import pl.polsl.wachowski.nutritionassistant.client.AlwaysFailingNutritionixClient;
import pl.polsl.wachowski.nutritionassistant.client.MockNutritionixClient;
import pl.polsl.wachowski.nutritionassistant.exception.provider.NutritionixException;
import pl.polsl.wachowski.nutritionix.client.NutritionixClient;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NutritionixFoodProviderAdapterTest {

    @Test
    @DisplayName("Should throw NutritionixException when Nutritionix food search result is failure")
    void shouldThrowNutritionixExceptionWhenNutritionixFoodSearchResultIsFailure() {
        //given
        final NutritionixClient nutritionixClient = new AlwaysFailingNutritionixClient();
        final NutritionixFoodProviderAdapter provider = new NutritionixFoodProviderAdapter(nutritionixClient);
        final Executable executable = () -> provider.searchFoods("potato");

        //when

        //then
        assertThrows(NutritionixException.class, executable);
    }

    @Test
    @DisplayName("Should return set of foods")
    void shouldReturnSetOfFoods() {
        //given
        final NutritionixClient nutritionixClient = new MockNutritionixClient();
        final NutritionixFoodProviderAdapter provider = new NutritionixFoodProviderAdapter(nutritionixClient);

        //when
        final Set<FoodBasicData> foods = provider.searchFoods("potato");

        //then
        assertNotNull(foods);
    }

    @Test
    @DisplayName("Should throw NutritionixException when Nutritionix food result is failure")
    void shouldThrowNutritionixExceptionWhenNutritionixFoodResultIsFailure() {
        //given
        final NutritionixClient nutritionixClient = new AlwaysFailingNutritionixClient();
        final NutritionixFoodProviderAdapter provider = new NutritionixFoodProviderAdapter(nutritionixClient);
        final Executable executable = () -> provider.getFood("12345");

        //when

        //then
        assertThrows(NutritionixException.class, executable);
    }

    @Test
    @DisplayName("Should return food")
    void shouldReturnFood() {
        //given
        final NutritionixClient nutritionixClient = new MockNutritionixClient();
        final NutritionixFoodProviderAdapter provider = new NutritionixFoodProviderAdapter(nutritionixClient);

        //when
        final Food food = provider.getFood("12345");

        //then
        assertNotNull(food);
    }

}