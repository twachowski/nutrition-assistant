package pl.polsl.wachowski.nutritionassistant.data.mapper.nutritionix;

import pl.polsl.wachowski.nutritionassistant.api.nutrients.Carbohydrate;
import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;

import java.util.HashMap;
import java.util.Map;

public final class CarbohydratesMapper implements NutrientMapper {

    private static final Map<Integer, Carbohydrate> carbohydrateMap = new HashMap<>(Carbohydrate.values().length);

    private static final CarbohydratesMapper instance = new CarbohydratesMapper();

    public static CarbohydratesMapper getInstance() {
        return instance;
    }

    private CarbohydratesMapper() {
        carbohydrateMap.put(269, Carbohydrate.SUGAR);
        carbohydrateMap.put(291, Carbohydrate.FIBER);
        carbohydrateMap.put(209, Carbohydrate.STARCH);
    }

    @Override
    public Carbohydrate get(final Integer id) {
        return carbohydrateMap.get(id);
    }

}
