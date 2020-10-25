package pl.polsl.wachowski.nutritionassistant.fdc.client.api.food.search;

import lombok.Value;

import java.util.Set;

@Value
public class FdcFoodSearchResponse {

    int totalHits;
    int currentPage;
    int totalPages;
    Set<FdcBasicFood> foods;

}
