package pl.polsl.wachowski.nutritionassistant.data.provider.fdc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.api.food.Food;
import pl.polsl.wachowski.nutritionassistant.api.food.FoodBasicData;
import pl.polsl.wachowski.nutritionassistant.api.food.NutrientDetails;
import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.api.nutrients.Lipid;
import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;
import pl.polsl.wachowski.nutritionassistant.data.mapper.fdc.*;
import pl.polsl.wachowski.nutritionassistant.data.provider.FoodDataProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.dto.details.fdc.FdcFoodDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.details.fdc.FdcNutrientDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.fdc.FdcFoodItemDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.fdc.FdcSearchResultDTO;
import pl.polsl.wachowski.nutritionassistant.util.LipidHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FdcProviderAdapter implements FoodDataProviderAdapter {

    private final List<NutrientMapper> nutrientMappers = Arrays.asList(GeneralNutrientsMapper.getInstance(),
                                                                       CarbohydratesMapper.getInstance(),
                                                                       LipidsMapper.getInstance(),
                                                                       AminoAcidsMapper.getInstance(),
                                                                       MineralsMapper.getInstance(),
                                                                       VitaminsMapper.getInstance());
    private final FdcProvider provider;
    private final LipidHelper lipidHelper;

    @Autowired
    public FdcProviderAdapter(final FdcProvider provider) {
        this.provider = provider;
        this.lipidHelper = new LipidHelper(LipidsMapper.OMEGA6_IDS,
                                           LipidsMapper.OMEGA3_ALA_IDS,
                                           LipidsMapper.OMEGA3_NON_ALA_IDS);
    }

    @Override
    public Set<FoodBasicData> search(String query) {
        final FdcSearchResultDTO result = provider.search(query);
        return result.getFoods()
                .stream()
                .map(FdcProviderAdapter::toFoodBasicData)
                .collect(Collectors.toSet());
    }

    @Override
    public Food getFood(final String id) {
        final FdcFoodDetailsDTO result = provider.getDetails(id);
        final List<FdcNutrientDTO> nutrients = result.getFoodNutrients();
        final Set<NutrientDetails> nutrientDetails = nutrients.stream()
                .filter(FdcProviderAdapter::isNotOmega)
                .map(this::toNutrientDetails)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        nutrientDetails.addAll(getTotalOmegaFattyAcids(nutrients));
        return new Food(result.getDescription(), result.getBrandOwner(), nutrientDetails);
    }

    @Override
    public NutritionDataProvider getProviderType() {
        return NutritionDataProvider.USDA;
    }

    private static FoodBasicData toFoodBasicData(final FdcFoodItemDTO food) {
        return new FoodBasicData(food.getFdcId().toString(),
                                 NutritionDataProvider.USDA,
                                 food.getDescription(),
                                 food.getBrandOwner());
    }

    private NutrientDetails toNutrientDetails(final FdcNutrientDTO nutrient) {
        return nutrientMappers.stream()
                .map(mapper -> mapper.get(nutrient.getId()))
                .filter(Objects::nonNull)
                .map(n -> new NutrientDetails(n, nutrient.getAmount()))
                .findFirst()
                .orElse(null);
    }

    private List<NutrientDetails> getTotalOmegaFattyAcids(final List<FdcNutrientDTO> nutrients) {
        final float omega6Amount = lipidHelper.getTotalOmega6Amount(nutrients);
        final float omega3Amount = lipidHelper.getTotalOmega3Amount(nutrients);
        final NutrientDetails totalOmega6 = new NutrientDetails(Lipid.OMEGA6, omega6Amount);
        final NutrientDetails totalOmega3 = new NutrientDetails(Lipid.OMEGA3, omega3Amount);
        return Arrays.asList(totalOmega6, totalOmega3);
    }

    private static boolean isNotOmega(final FdcNutrientDTO nutrient) {
        return !LipidsMapper.OMEGA_ACIDS_IDS.contains(nutrient.getId());
    }

}
