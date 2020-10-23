package pl.polsl.wachowski.nutritionassistant.dto.search.fdc;

import lombok.Value;

import java.util.List;

@Value
public class FdcSearchResultDTO {

    Integer totalHits;

    Integer currentPage;

    Integer totalPages;

    List<FdcFoodItemDTO> foods;

}
