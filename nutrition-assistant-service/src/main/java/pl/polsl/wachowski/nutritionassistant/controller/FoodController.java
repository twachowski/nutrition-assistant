package pl.polsl.wachowski.nutritionassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.wachowski.nutritionassistant.api.food.*;
import pl.polsl.wachowski.nutritionassistant.service.FoodService;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pl.polsl.wachowski.nutritionassistant.api.NutritionAssistantApi.*;

@RestController
@RequestMapping(FOODS_API_SUFFIX)
public class FoodController {

    private final FoodService foodService;

    @Autowired
    public FoodController(final FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    ResponseEntity<FoodSearchResponse> searchFoods(@RequestParam(QUERY) final String query) {
        final Set<FoodBasicData> foods = foodService.searchFoods(query);
        final FoodSearchResponse response = new FoodSearchResponse(foods);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = FOOD_ID_API_SUFFIX,
                produces = APPLICATION_JSON_VALUE)
    ResponseEntity<FoodResponse> getFood(@PathVariable(FOOD_ID) final String foodId,
                                         @RequestParam(PROVIDER) final NutritionDataProvider nutritionDataProvider) {
        final Food food = foodService.getFood(foodId, nutritionDataProvider);
        final FoodResponse response = new FoodResponse(food);
        return ResponseEntity.ok(response);
    }

}
