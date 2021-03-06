package pl.polsl.wachowski.nutritionassistant.data.mapper.fdc;

import pl.polsl.wachowski.nutritionassistant.api.nutrients.GeneralNutrient;
import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;

import java.util.HashMap;
import java.util.Map;

public class GeneralNutrientsMapper implements NutrientMapper {

    private static final Map<Integer, GeneralNutrient> generalNutrientsMap = new HashMap<>(GeneralNutrient.values().length);

    private static final GeneralNutrientsMapper instance = new GeneralNutrientsMapper();

    public static GeneralNutrientsMapper getInstance() {
        return instance;
    }

    private GeneralNutrientsMapper() {
        generalNutrientsMap.put(1008, GeneralNutrient.ENERGY);
        generalNutrientsMap.put(1005, GeneralNutrient.TOTAL_CARBS);
        generalNutrientsMap.put(1004, GeneralNutrient.TOTAL_FAT);
        generalNutrientsMap.put(1003, GeneralNutrient.TOTAL_PROTEIN);
        generalNutrientsMap.put(1051, GeneralNutrient.WATER);
        generalNutrientsMap.put(1057, GeneralNutrient.CAFFEINE);
        generalNutrientsMap.put(1018, GeneralNutrient.ALCOHOL);
    }

    @Override
    public GeneralNutrient get(final Integer id) {
        return generalNutrientsMap.get(id);
    }

}
