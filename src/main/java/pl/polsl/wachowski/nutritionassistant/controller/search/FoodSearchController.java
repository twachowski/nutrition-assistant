package pl.polsl.wachowski.nutritionassistant.controller.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.wachowski.nutritionassistant.dto.search.FoodSearchCriteriaDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.FoodSearchItemDTO;
import pl.polsl.wachowski.nutritionassistant.service.FoodService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodSearchController {

    private final FoodService foodService;

    @Autowired
    public FoodSearchController(final FoodService foodService) {
        this.foodService = foodService;
    }

    @RequestMapping(
            path = "/search",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity searchFood(@RequestBody @Valid final FoodSearchCriteriaDTO criteria) {
        final List<FoodSearchItemDTO> foods = foodService.search(criteria.getQuery());
        return ResponseEntity.ok(foods);
    }

}
