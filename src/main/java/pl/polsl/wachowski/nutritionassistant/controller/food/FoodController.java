package pl.polsl.wachowski.nutritionassistant.controller.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.wachowski.nutritionassistant.dto.details.FoodDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.details.FoodDetailsRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.FoodSearchCriteriaDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.FoodSearchItemDTO;
import pl.polsl.wachowski.nutritionassistant.service.FoodService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {

    private final FoodService foodService;

    @Autowired
    public FoodController(final FoodService foodService) {
        this.foodService = foodService;
    }

    @RequestMapping(
            path = "/search",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FoodSearchItemDTO>> searchFood(@RequestBody @Valid final FoodSearchCriteriaDTO criteria) {
        final List<FoodSearchItemDTO> foods = foodService.search(criteria.getQuery());
        return ResponseEntity.ok(foods);
    }

    @RequestMapping(
            path = "/details",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FoodDetailsDTO> getDetails(@RequestBody @Valid final FoodDetailsRequestDTO request) {
        final FoodDetailsDTO foodDetails = foodService.getDetails(request.getExternalId(), request.getProvider());
        return ResponseEntity.ok(foodDetails);
    }

}
