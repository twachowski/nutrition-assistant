package pl.polsl.wachowski.nutritionassistant.data.mapper.nutritionix;

import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;
import pl.polsl.wachowski.nutritionassistant.api.nutrients.Vitamin;

import java.util.HashMap;
import java.util.Map;

public final class VitaminsMapper implements NutrientMapper {

    private static final Map<Integer, Vitamin> vitaminMap = new HashMap<>(Vitamin.values().length);

    private static final VitaminsMapper instance = new VitaminsMapper();

    public static VitaminsMapper getInstance() {
        return instance;
    }

    private VitaminsMapper() {
        vitaminMap.put(318, Vitamin.A);
        vitaminMap.put(404, Vitamin.B1);
        vitaminMap.put(405, Vitamin.B2);
        vitaminMap.put(406, Vitamin.B3);
        vitaminMap.put(421, Vitamin.B4);
        vitaminMap.put(410, Vitamin.B5);
        vitaminMap.put(415, Vitamin.B6);
        vitaminMap.put(418, Vitamin.B12);
        vitaminMap.put(401, Vitamin.C);
        vitaminMap.put(324, Vitamin.D);
        vitaminMap.put(323, Vitamin.E);   //TODO only alpha-tocopherol
        vitaminMap.put(417, Vitamin.FOLATE);
        vitaminMap.put(430, Vitamin.K);
    }

    @Override
    public Vitamin get(final Integer id) {
        return vitaminMap.get(id);
    }

}
