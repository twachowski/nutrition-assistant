package pl.polsl.wachowski.nutritionassistant.data.mapper.nutritionix;

import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;
import pl.polsl.wachowski.nutritionassistant.api.nutrients.Mineral;

import java.util.HashMap;
import java.util.Map;

public final class MineralsMapper implements NutrientMapper {

    private static final Map<Integer, Mineral> mineralMap = new HashMap<>(Mineral.values().length);

    private static final MineralsMapper instance = new MineralsMapper();

    public static MineralsMapper getInstance() {
        return instance;
    }

    private MineralsMapper() {
        mineralMap.put(301, Mineral.CALCIUM);
        mineralMap.put(312, Mineral.COPPER);
        mineralMap.put(313, Mineral.FLUORIDE);
        mineralMap.put(303, Mineral.IRON);
        mineralMap.put(304, Mineral.MAGNESIUM);
        mineralMap.put(315, Mineral.MANGANESE);
        mineralMap.put(305, Mineral.PHOSPHORUS);
        mineralMap.put(306, Mineral.POTASSIUM);
        mineralMap.put(317, Mineral.SELENIUM);
        mineralMap.put(307, Mineral.SODIUM);
        mineralMap.put(309, Mineral.ZINC);
    }

    @Override
    public Mineral get(final Integer id) {
        return mineralMap.get(id);
    }

}
