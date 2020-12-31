package pl.polsl.wachowski.nutritionassistant.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.facade.FoodFacade;
import pl.polsl.wachowski.nutritionassistant.samples.FoodSamples;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.polsl.wachowski.nutritionassistant.api.NutritionAssistantApi.*;

@WebMvcTest(value = FoodController.class,
            excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {
        FoodController.class,
        ControllerExceptionHandler.class
})
class FoodControllerValidationTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private FoodFacade foodFacade;

    @BeforeEach
    void setUp() {
        Mockito.when(foodFacade.searchFoods("potato"))
                .thenReturn(Collections.emptySet());
        Mockito.when(foodFacade.getFood("12345", NutritionDataProvider.USDA))
                .thenReturn(FoodSamples.mockFood());
    }

    @Test
    @DisplayName("Should fail validation when trying to search foods with null query parameter")
    void shouldFailValidationWhenTryingToSearchFoodsWithNullQueryParameter() throws Exception {
        //given

        //when

        //then
        mvc.perform(get(FOODS_API_SUFFIX))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to search foods with empty query parameter")
    void shouldFailValidationWhenTryingToSearchFoodsWithEmptyQueryParameter() throws Exception {
        //given
        final String query = "";

        //when

        //then
        mvc.perform(get(FOODS_API_SUFFIX)
                            .param(QUERY, query))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to search foods with blank query parameter")
    void shouldFailValidationWhenTryingToSearchFoodsWithBlankQueryParameter() throws Exception {
        //given
        final String query = "   ";

        //when

        //then
        mvc.perform(get(FOODS_API_SUFFIX)
                            .param(QUERY, query))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return set of foods")
    void shouldReturnSetOfFoods() throws Exception {
        //given
        final String query = "potato";

        //when

        //then
        mvc.perform(get(FOODS_API_SUFFIX)
                            .param(QUERY, query))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("foods").isArray());
    }

    @Test
    @DisplayName("Should fail validation when trying to get food with null food id path parameter")
    void shouldFailValidationWhenTryingToGetFoodWithNullFoodIdPathParameter() throws Exception {
        //given
        final String foodId = null;
        final String provider = NutritionDataProvider.USDA.name();

        //when

        //then
        mvc.perform(get(FOODS_FOOD_ID_API_SUFFIX, foodId)
                            .param(PROVIDER, provider))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to get food with empty food id path parameter")
    void shouldFailValidationWhenTryingToGetFoodWithEmptyFoodIdPathParameter() throws Exception {
        //given
        final String foodId = "";
        final String provider = NutritionDataProvider.USDA.name();

        //when

        //then
        mvc.perform(get(FOODS_FOOD_ID_API_SUFFIX, foodId)
                            .param(PROVIDER, provider))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to get food with blank food id path parameter")
    void shouldFailValidationWhenTryingToGetFoodWithBlankFoodIdPathParameter() throws Exception {
        //given
        final String foodId = "   ";
        final String provider = NutritionDataProvider.USDA.name();

        //when

        //then
        mvc.perform(get(FOODS_FOOD_ID_API_SUFFIX, foodId)
                            .param(PROVIDER, provider))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to get food with null provider request parameter")
    void shouldFailValidationWhenTryingToGetFoodWithNullProviderRequestParameter() throws Exception {
        //given
        final String foodId = "12345";

        //when

        //then
        mvc.perform(get(FOODS_FOOD_ID_API_SUFFIX, foodId))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to get food with non-existing provider request parameter")
    void shouldFailValidationWhenTryingToGetFoodWithNonExistingProviderRequestParameter() throws Exception {
        //given
        final String foodId = "12345";
        final String provider = "Non-existing provider";

        //when

        //then
        mvc.perform(get(FOODS_FOOD_ID_API_SUFFIX, foodId)
                            .param(PROVIDER, provider))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return food")
    void shouldReturnFood() throws Exception {
        //given
        final String foodId = "12345";
        final String provider = NutritionDataProvider.USDA.name();

        //when

        //then
        mvc.perform(get(FOODS_FOOD_ID_API_SUFFIX, foodId)
                            .param(PROVIDER, provider))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("food").exists());
    }

}