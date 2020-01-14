package pl.polsl.wachowski.nutritionassistant.data.mapper.fdc;

import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.Carbohydrate;

import java.util.HashMap;
import java.util.Map;

public class CarbohydratesMapper implements NutrientMapper {

    private static final Map<Integer, Carbohydrate> carbohydrateMap = new HashMap<>(Carbohydrate.values().length);

    private static final CarbohydratesMapper instance = new CarbohydratesMapper();

    public static CarbohydratesMapper getInstance() {
        return instance;
    }

    private CarbohydratesMapper() {
        carbohydrateMap.put(2000, Carbohydrate.SUGAR);
        carbohydrateMap.put(1079, Carbohydrate.FIBER);
        carbohydrateMap.put(1009, Carbohydrate.STARCH);
    }

    @Override
    public Carbohydrate get(final Integer id) {
        return carbohydrateMap.get(id);
    }

}
