package pl.polsl.wachowski.nutritionassistant.data.mapper.fdc;

import pl.polsl.wachowski.nutritionassistant.api.nutrients.Mineral;
import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;

import java.util.HashMap;
import java.util.Map;

public class MineralsMapper implements NutrientMapper {

    private static final Map<Integer, Mineral> mineralMap = new HashMap<>(Mineral.values().length);

    private static final MineralsMapper instance = new MineralsMapper();

    public static MineralsMapper getInstance() {
        return instance;
    }

    private MineralsMapper() {
        mineralMap.put(1087, Mineral.CALCIUM);
        mineralMap.put(1098, Mineral.COPPER);
        mineralMap.put(1089, Mineral.IRON);
        mineralMap.put(1090, Mineral.MAGNESIUM);
        mineralMap.put(1101, Mineral.MANGANESE);
        mineralMap.put(1091, Mineral.PHOSPHORUS);
        mineralMap.put(1092, Mineral.POTASSIUM);
        mineralMap.put(1103, Mineral.SELENIUM);
        mineralMap.put(1093, Mineral.SODIUM);
        mineralMap.put(1095, Mineral.ZINC);
    }

    @Override
    public Mineral get(final Integer id) {
        return mineralMap.get(id);
    }

}
