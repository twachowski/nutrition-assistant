package pl.polsl.wachowski.nutritionassistant.data.fdc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.data.FoodDataProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.dto.details.FoodDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.details.fdc.FdcFoodDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.FoodSearchItemDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.fdc.FdcFoodItemDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.fdc.FdcSearchResultDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FdcProviderAdapter implements FoodDataProviderAdapter {

    private final FdcProvider provider;

    @Autowired
    public FdcProviderAdapter(final FdcProvider provider) {
        this.provider = provider;
    }

    @Override
    public List<FoodSearchItemDTO> search(String query) {
        final FdcSearchResultDTO result = provider.search(query);
        return result.getFoods()
                .stream()
                .map(FdcProviderAdapter::mapFdcFoodItem)
                .collect(Collectors.toList());
    }

    @Override
    public FoodDetailsDTO getDetails(final String id) {
        final FdcFoodDetailsDTO result = provider.getDetails(id);
        return null;
    }

    @Override
    public NutritionDataProvider getProviderType() {
        return NutritionDataProvider.USDA;
    }

    private static FoodSearchItemDTO mapFdcFoodItem(final FdcFoodItemDTO item) {
        return new FoodSearchItemDTO(
                item.getDescription(),
                item.getBrandOwner(),
                item.getFdcId().toString(),
                NutritionDataProvider.USDA);
    }

}
