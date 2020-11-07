package pl.polsl.wachowski.nutritionassistant.provider.food;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.fdc.client.FdcClient;
import pl.polsl.wachowski.fdc.client.FdcResult;
import pl.polsl.wachowski.fdc.client.api.food.FdcFood;
import pl.polsl.wachowski.fdc.client.api.food.FdcNutrient;
import pl.polsl.wachowski.fdc.client.api.food.search.FdcBasicFood;
import pl.polsl.wachowski.fdc.client.api.food.search.FdcFoodSearchResponse;
import pl.polsl.wachowski.nutritionassistant.api.food.Food;
import pl.polsl.wachowski.nutritionassistant.api.food.FoodBasicData;
import pl.polsl.wachowski.nutritionassistant.api.food.NutrientDetails;
import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.api.nutrients.Lipid;
import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;
import pl.polsl.wachowski.nutritionassistant.data.mapper.fdc.*;
import pl.polsl.wachowski.nutritionassistant.data.ExternalNutrientDetails;
import pl.polsl.wachowski.nutritionassistant.exception.provider.FdcException;
import pl.polsl.wachowski.nutritionassistant.util.LipidHelper;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FdcFoodProviderAdapter implements FoodProvider {

    private static final List<NutrientMapper> NUTRIENT_MAPPERS = Arrays.asList(GeneralNutrientsMapper.getInstance(),
                                                                               CarbohydratesMapper.getInstance(),
                                                                               LipidsMapper.getInstance(),
                                                                               AminoAcidsMapper.getInstance(),
                                                                               MineralsMapper.getInstance(),
                                                                               VitaminsMapper.getInstance());
    private static final LipidHelper LIPID_HELPER = new LipidHelper(LipidsMapper.OMEGA6_IDS,
                                                                    LipidsMapper.OMEGA3_ALA_IDS,
                                                                    LipidsMapper.OMEGA3_NON_ALA_IDS);
    private final FdcClient fdcClient;

    @Autowired
    public FdcFoodProviderAdapter(final FdcClient fdcClient) {
        this.fdcClient = fdcClient;
    }

    @Override
    public Set<FoodBasicData> searchFoods(final String query) {
        final FdcResult<FdcFoodSearchResponse> result = fdcClient.searchFoods(query);
        if (result.isFailure()) {
            log.error("Failed to search foods in USDA, query={}, result={}", query, result);
            throw new FdcException("Failed to search foods in USDA", result.exception());
        }

        return result.response()
                .getFoods()
                .stream()
                .map(FdcFoodProviderAdapter::toFoodBasicData)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public Food getFood(final String id) {
        final FdcResult<FdcFood> result = fdcClient.getFood(Long.parseLong(id));
        if (result.isFailure()) {
            log.error("Failed to retrieve food data from USDA, foodId={}, result={}", id, result);
            throw new FdcException("Failed to get USDA food by id: " + id, result.exception());
        }

        final FdcFood food = result.response();
        final Set<NutrientDetails> nutrientDetails = food.getNutrients()
                .stream()
                .filter(FdcFoodProviderAdapter::isNotOmega)
                .map(FdcFoodProviderAdapter::toNutrientDetails)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        nutrientDetails.addAll(getTotalOmegaFattyAcids(food.getNutrients()));
        return new Food(food.getDescription(), food.getBrandOwner(), nutrientDetails);
    }

    @Override
    public NutritionDataProvider getType() {
        return NutritionDataProvider.USDA;
    }

    private static FoodBasicData toFoodBasicData(final FdcBasicFood food) {
        return new FoodBasicData(String.valueOf(food.getId()),
                                 NutritionDataProvider.USDA,
                                 food.getDescription(),
                                 food.getBrandOwner());
    }

    private static NutrientDetails toNutrientDetails(final FdcNutrient nutrient) {
        return NUTRIENT_MAPPERS.stream()
                .map(mapper -> mapper.get(nutrient.getId()))
                .filter(Objects::nonNull)
                .map(n -> new NutrientDetails(n, nutrient.getAmount()))
                .findFirst()
                .orElse(null);
    }

    private static List<NutrientDetails> getTotalOmegaFattyAcids(final Set<FdcNutrient> nutrients) {
        final Set<ExternalNutrientDetails> externalNutrients = nutrients.stream()
                .map(FdcFoodProviderAdapter::toExternalNutrientDetails)
                .collect(Collectors.toSet());
        final float omega6Amount = LIPID_HELPER.getTotalOmega6Amount(externalNutrients);
        final float omega3Amount = LIPID_HELPER.getTotalOmega3Amount(externalNutrients);
        final NutrientDetails totalOmega6 = new NutrientDetails(Lipid.OMEGA6, omega6Amount);
        final NutrientDetails totalOmega3 = new NutrientDetails(Lipid.OMEGA3, omega3Amount);
        return Arrays.asList(totalOmega6, totalOmega3);
    }

    private static boolean isNotOmega(final FdcNutrient nutrient) {
        return !LipidsMapper.OMEGA_ACIDS_IDS.contains(nutrient.getId());
    }

    private static ExternalNutrientDetails toExternalNutrientDetails(final FdcNutrient nutrient) {
        return new ExternalNutrientDetails() {
            @Override
            public Integer getId() {
                return nutrient.getId();
            }

            @Override
            public Float getAmount() {
                return nutrient.getAmount();
            }
        };
    }

}
