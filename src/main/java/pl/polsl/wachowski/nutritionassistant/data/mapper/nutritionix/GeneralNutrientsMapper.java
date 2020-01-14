package pl.polsl.wachowski.nutritionassistant.data.mapper.nutritionix;

import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.GeneralNutrient;

import java.util.HashMap;
import java.util.Map;

public final class GeneralNutrientsMapper implements NutrientMapper {

    private static final Map<Integer, GeneralNutrient> generalNutrientsMap = new HashMap<>(GeneralNutrient.values().length);

    private static final GeneralNutrientsMapper instance = new GeneralNutrientsMapper();

    public static GeneralNutrientsMapper getInstance() {
        return instance;
    }

    private GeneralNutrientsMapper() {
        generalNutrientsMap.put(208, GeneralNutrient.ENERGY);
        generalNutrientsMap.put(205, GeneralNutrient.TOTAL_CARBS);
        generalNutrientsMap.put(204, GeneralNutrient.TOTAL_FAT);
        generalNutrientsMap.put(203, GeneralNutrient.TOTAL_PROTEIN);
        generalNutrientsMap.put(255, GeneralNutrient.WATER);
        generalNutrientsMap.put(262, GeneralNutrient.CAFFEINE);
        generalNutrientsMap.put(221, GeneralNutrient.ALCOHOL);
    }

    @Override
    public GeneralNutrient get(final Integer id) {
        return generalNutrientsMap.get(id);
    }

}
