package pl.polsl.wachowski.nutritionassistant.data.provider.nutritionix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.api.food.Food;
import pl.polsl.wachowski.nutritionassistant.api.food.FoodBasicData;
import pl.polsl.wachowski.nutritionassistant.api.food.NutrientDetails;
import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.api.nutrients.Lipid;
import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;
import pl.polsl.wachowski.nutritionassistant.data.mapper.nutritionix.*;
import pl.polsl.wachowski.nutritionassistant.data.provider.FoodDataProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.dto.details.nutritionix.NutritionixFoodDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.details.nutritionix.NutritionixNutrientDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.nutritionix.NutritionixFood;
import pl.polsl.wachowski.nutritionassistant.dto.search.nutritionix.NutritionixSearchResultDTO;
import pl.polsl.wachowski.nutritionassistant.util.LipidHelper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class NutritionixProviderAdapter implements FoodDataProviderAdapter {

    private final List<NutrientMapper> nutrientMappers = Arrays.asList(GeneralNutrientsMapper.getInstance(),
                                                                       CarbohydratesMapper.getInstance(),
                                                                       LipidsMapper.getInstance(),
                                                                       AminoAcidsMapper.getInstance(),
                                                                       MineralsMapper.getInstance(),
                                                                       VitaminsMapper.getInstance());
    private final NutritionixProvider provider;
    private final LipidHelper lipidHelper;

    @Autowired
    public NutritionixProviderAdapter(final NutritionixProvider provider) {
        this.provider = provider;
        this.lipidHelper = new LipidHelper(LipidsMapper.OMEGA6_IDS,
                                           LipidsMapper.OMEGA3_ALA_IDS,
                                           LipidsMapper.OMEGA3_NON_ALA_IDS);
    }

    @Override
    public Set<FoodBasicData> search(String query) {
        final NutritionixSearchResultDTO result = provider.search(query);
        return Stream.of(result.getCommonFoods(), result.getBrandedFoods())
                .flatMap(List::stream)
                .map(NutritionixProviderAdapter::toFoodBasicData)
                .collect(Collectors.toSet());
    }

    @Override
    public Food getFood(final String id) {
        final NutritionixFoodDetailsDTO result = provider.getDetails(id);
        final List<NutritionixNutrientDTO> nutrients = result.getNutrients();
        final Set<NutrientDetails> nutrientDetails = nutrients.stream()
                .filter(NutritionixProviderAdapter::isNotOmega)
                .map(this::toNutrientDetails)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        nutrientDetails.addAll(getTotalOmegaFattyAcids(nutrients));
        return new Food(result.getName(), result.getBrandName(), nutrientDetails);
    }

    @Override
    public NutritionDataProvider getProviderType() {
        return NutritionDataProvider.NUTRITIONIX;
    }

    private static FoodBasicData toFoodBasicData(final NutritionixFood food) {
        return new FoodBasicData(food.getId(),
                                 NutritionDataProvider.NUTRITIONIX,
                                 food.getFoodName(),
                                 food.getBrandName());
    }

    private NutrientDetails toNutrientDetails(final NutritionixNutrientDTO nutrient) {
        return nutrientMappers.stream()
                .map(mapper -> mapper.get(nutrient.getId()))
                .filter(Objects::nonNull)
                .map(n -> new NutrientDetails(n, nutrient.getAmount()))
                .findFirst()
                .orElse(null);
    }

    private List<NutrientDetails> getTotalOmegaFattyAcids(final List<NutritionixNutrientDTO> nutrients) {
        final float omega6Amount = lipidHelper.getTotalOmega6Amount(nutrients);
        final float omega3Amount = lipidHelper.getTotalOmega3Amount(nutrients);
        final NutrientDetails totalOmega6 = new NutrientDetails(Lipid.OMEGA6, omega6Amount);
        final NutrientDetails totalOmega3 = new NutrientDetails(Lipid.OMEGA3, omega3Amount);
        return Arrays.asList(totalOmega6, totalOmega3);
    }

    private static boolean isNotOmega(final NutritionixNutrientDTO nutrient) {
        return !LipidsMapper.OMEGA_ACIDS_IDS.contains(nutrient.getId());
    }

}
