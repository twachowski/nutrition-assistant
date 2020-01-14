package pl.polsl.wachowski.nutritionassistant.data.provider.fdc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;
import pl.polsl.wachowski.nutritionassistant.data.mapper.fdc.*;
import pl.polsl.wachowski.nutritionassistant.data.provider.FoodDataProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.Nutrient;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.dto.details.FoodDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.details.NutrientDetailDTO;
import pl.polsl.wachowski.nutritionassistant.dto.details.fdc.FdcFoodDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.details.fdc.FdcNutrientDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.FoodSearchItemDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.fdc.FdcFoodItemDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.fdc.FdcSearchResultDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FdcProviderAdapter implements FoodDataProviderAdapter {

    private final FdcProvider provider;

    private final List<NutrientMapper> nutrientMappers = new ArrayList<>(6);

    @Autowired
    public FdcProviderAdapter(final FdcProvider provider) {
        this.provider = provider;
        nutrientMappers.add(GeneralNutrientsMapper.getInstance());
        nutrientMappers.add(CarbohydratesMapper.getInstance());
        nutrientMappers.add(LipidsMapper.getInstance());
        nutrientMappers.add(AminoAcidsMapper.getInstance());
        nutrientMappers.add(MineralsMapper.getInstance());
        nutrientMappers.add(VitaminsMapper.getInstance());
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
        final List<FdcNutrientDTO> nutrients = result.getFoodNutrients();
        final List<NutrientDetailDTO> nutrientDetails =
                nutrients
                        .stream()
                        .map(this::toNutrientDetail)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
        return new FoodDetailsDTO(nutrientDetails);
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

    private NutrientDetailDTO toNutrientDetail(final FdcNutrientDTO nutrient) {
        for (final NutrientMapper mapper : nutrientMappers) {
            final Nutrient n = mapper.get(nutrient.getId());
            if (n != null) {
                return new NutrientDetailDTO(n, nutrient.getAmount());
            }
        }
        return null;
    }

}
