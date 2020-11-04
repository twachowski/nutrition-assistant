package pl.polsl.wachowski.nutritionassistant.provider.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.api.food.Food;
import pl.polsl.wachowski.nutritionassistant.api.food.FoodBasicData;
import pl.polsl.wachowski.nutritionassistant.api.food.NutrientDetails;
import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.api.nutrients.Lipid;
import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;
import pl.polsl.wachowski.nutritionassistant.data.mapper.nutritionix.*;
import pl.polsl.wachowski.nutritionassistant.data.ExternalNutrientDetails;
import pl.polsl.wachowski.nutritionassistant.exception.provider.NutritionixException;
import pl.polsl.wachowski.nutritionassistant.util.LipidHelper;
import pl.polsl.wachowski.nutritionix.client.NutritionixClient;
import pl.polsl.wachowski.nutritionix.client.NutritionixResult;
import pl.polsl.wachowski.nutritionix.client.api.food.NutritionixFood;
import pl.polsl.wachowski.nutritionix.client.api.food.NutritionixNutrient;
import pl.polsl.wachowski.nutritionix.client.api.food.search.NutritionixBasicFood;
import pl.polsl.wachowski.nutritionix.client.api.food.search.NutritionixFoodSearchResponse;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class NutritionixFoodProviderAdapter implements FoodProvider {

    private static final List<NutrientMapper> NUTRIENT_MAPPERS = Arrays.asList(GeneralNutrientsMapper.getInstance(),
                                                                               CarbohydratesMapper.getInstance(),
                                                                               LipidsMapper.getInstance(),
                                                                               AminoAcidsMapper.getInstance(),
                                                                               MineralsMapper.getInstance(),
                                                                               VitaminsMapper.getInstance());
    private static final LipidHelper LIPID_HELPER = new LipidHelper(LipidsMapper.OMEGA6_IDS,
                                                                    LipidsMapper.OMEGA3_ALA_IDS,
                                                                    LipidsMapper.OMEGA3_NON_ALA_IDS);
    private final NutritionixClient nutritionixClient;

    @Autowired
    public NutritionixFoodProviderAdapter(final NutritionixClient nutritionixClient) {
        this.nutritionixClient = nutritionixClient;
    }

    @Override
    public Set<FoodBasicData> searchFoods(final String query) {
        final NutritionixResult<NutritionixFoodSearchResponse> result = nutritionixClient.searchFoods(query);
        if (result.isFailure()) {
            throw new NutritionixException("Failed to search foods in Nutritionix", result.exception());
        }

        final NutritionixFoodSearchResponse response = result.response();
        return Stream.of(response.getCommonFoods(), response.getBrandedFoods())
                .flatMap(List::stream)
                .map(NutritionixFoodProviderAdapter::toFoodBasicData)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public Food getFood(final String id) {
        final NutritionixResult<NutritionixFood> result = nutritionixClient.getFood(id);
        if (result.isFailure()) {
            throw new NutritionixException("Failed to get Nutritionix food by id: " + id, result.exception());
        }

        final NutritionixFood food = result.response();
        final Set<NutrientDetails> nutrientDetails = food.getNutrients()
                .stream()
                .filter(NutritionixFoodProviderAdapter::isNotOmega)
                .map(NutritionixFoodProviderAdapter::toNutrientDetails)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        nutrientDetails.addAll(getTotalOmegaFattyAcids(food.getNutrients()));
        return new Food(food.getName(), food.getBrandName(), nutrientDetails);
    }

    @Override
    public NutritionDataProvider getType() {
        return NutritionDataProvider.NUTRITIONIX;
    }

    private static List<NutrientDetails> getTotalOmegaFattyAcids(final Set<NutritionixNutrient> nutrients) {
        final Set<ExternalNutrientDetails> externalNutrients = nutrients.stream()
                .map(NutritionixFoodProviderAdapter::toExternalNutrientDetails)
                .collect(Collectors.toSet());
        final float omega6Amount = LIPID_HELPER.getTotalOmega6Amount(externalNutrients);
        final float omega3Amount = LIPID_HELPER.getTotalOmega3Amount(externalNutrients);
        final NutrientDetails totalOmega6 = new NutrientDetails(Lipid.OMEGA6, omega6Amount);
        final NutrientDetails totalOmega3 = new NutrientDetails(Lipid.OMEGA3, omega3Amount);
        return Arrays.asList(totalOmega6, totalOmega3);
    }

    private static FoodBasicData toFoodBasicData(final NutritionixBasicFood food) {
        return new FoodBasicData(food.getId(),
                                 NutritionDataProvider.NUTRITIONIX,
                                 food.getFoodName(),
                                 food.getBrandName());
    }

    private static boolean isNotOmega(final NutritionixNutrient nutrient) {
        return !LipidsMapper.OMEGA_ACIDS_IDS.contains(nutrient.getId());
    }

    private static NutrientDetails toNutrientDetails(final NutritionixNutrient nutrient) {
        return NUTRIENT_MAPPERS.stream()
                .map(mapper -> mapper.get(nutrient.getId()))
                .filter(Objects::nonNull)
                .map(n -> new NutrientDetails(n, nutrient.getAmount()))
                .findFirst()
                .orElse(null);
    }

    private static ExternalNutrientDetails toExternalNutrientDetails(final NutritionixNutrient nutrient) {
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
