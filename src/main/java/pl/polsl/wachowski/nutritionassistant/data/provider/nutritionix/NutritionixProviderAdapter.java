package pl.polsl.wachowski.nutritionassistant.data.provider.nutritionix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.data.provider.FoodDataProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;
import pl.polsl.wachowski.nutritionassistant.data.mapper.nutritionix.*;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.*;
import pl.polsl.wachowski.nutritionassistant.dto.details.FoodDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.details.NutrientDetailDTO;
import pl.polsl.wachowski.nutritionassistant.dto.details.nutritionix.NutritionixFoodDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.details.nutritionix.NutritionixNutrientDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.FoodSearchItemDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.nutritionix.NutritionixBrandedFoodItemDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.nutritionix.NutritionixCommonFoodItemDTO;
import pl.polsl.wachowski.nutritionassistant.dto.search.nutritionix.NutritionixSearchResultDTO;
import pl.polsl.wachowski.nutritionassistant.util.LipidHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class NutritionixProviderAdapter implements FoodDataProviderAdapter {

    private final NutritionixProvider provider;

    private final List<NutrientMapper> nutrientMappers = new ArrayList<>(6);

    private final LipidHelper lipidHelper;

    @Autowired
    public NutritionixProviderAdapter(final NutritionixProvider provider) {
        this.provider = provider;

        nutrientMappers.add(GeneralNutrientsMapper.getInstance());
        nutrientMappers.add(CarbohydratesMapper.getInstance());
        nutrientMappers.add(LipidsMapper.getInstance());
        nutrientMappers.add(AminoAcidsMapper.getInstance());
        nutrientMappers.add(MineralsMapper.getInstance());
        nutrientMappers.add(VitaminsMapper.getInstance());

        lipidHelper = new LipidHelper(
                LipidsMapper.OMEGA6_IDS,
                LipidsMapper.OMEGA3_ALA_IDS,
                LipidsMapper.OMEGA3_NON_ALA_IDS);
    }

    @Override
    public List<FoodSearchItemDTO> search(String query) {
        final NutritionixSearchResultDTO result = provider.search(query);
        final List<NutritionixCommonFoodItemDTO> commonFoods = result.getCommonFoods();
        final List<NutritionixBrandedFoodItemDTO> brandedFoods = result.getBrandedFoods();

        final List<FoodSearchItemDTO> foods = new ArrayList<>(commonFoods.size() + brandedFoods.size());
        commonFoods
                .stream()
                .map(NutritionixProviderAdapter::mapCommonFood)
                .forEach(foods::add);
        brandedFoods
                .stream()
                .map(NutritionixProviderAdapter::mapBrandedFood)
                .forEach(foods::add);

        return foods;
    }

    @Override
    public FoodDetailsDTO getDetails(final String id) {
        final NutritionixFoodDetailsDTO result = provider.getDetails(id);
        final List<NutritionixNutrientDTO> nutrients = result.getNutrients();
        final List<NutrientDetailDTO> nutrientDetails =
                nutrients
                        .stream()
                        .filter(NutritionixProviderAdapter::isNotOmega)
                        .map(this::toNutrientDetail)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
        nutrientDetails.addAll(getTotalOmegaFattyAcids(nutrients));
        return new FoodDetailsDTO(result.getName(), result.getBrandName(), nutrientDetails);
    }

    @Override
    public NutritionDataProvider getProviderType() {
        return NutritionDataProvider.NUTRITIONIX;
    }

    private static FoodSearchItemDTO mapCommonFood(final NutritionixCommonFoodItemDTO item) {
        return new FoodSearchItemDTO(
                item.getFoodName(),
                null,
                item.getFoodName(),
                NutritionDataProvider.NUTRITIONIX);
    }

    private static FoodSearchItemDTO mapBrandedFood(final NutritionixBrandedFoodItemDTO item) {
        return new FoodSearchItemDTO(
                item.getFoodName(),
                item.getBrandName(),
                item.getBrandId(),
                NutritionDataProvider.NUTRITIONIX);
    }

    private NutrientDetailDTO toNutrientDetail(final NutritionixNutrientDTO nutrient) {
        for (final NutrientMapper mapper : nutrientMappers) {
            final Nutrient n = mapper.get(nutrient.getId());
            if (n != null) {
                return new NutrientDetailDTO(n, nutrient.getAmount());
            }
        }
        return null;
    }

    private List<NutrientDetailDTO> getTotalOmegaFattyAcids(final List<NutritionixNutrientDTO> nutrients) {
        final float omega6Amount = lipidHelper.getTotalOmega6Amount(nutrients);
        final float omega3Amount = lipidHelper.getTotalOmega3Amount(nutrients);
        final NutrientDetailDTO totalOmega6 = new NutrientDetailDTO(Lipid.OMEGA6, omega6Amount);
        final NutrientDetailDTO totalOmega3 = new NutrientDetailDTO(Lipid.OMEGA3, omega3Amount);
        return Arrays.asList(totalOmega6, totalOmega3);
    }

    private static boolean isNotOmega(final NutritionixNutrientDTO nutrient) {
        return !LipidsMapper.OMEGA_ACIDS_IDS.contains(nutrient.getId());
    }

}
