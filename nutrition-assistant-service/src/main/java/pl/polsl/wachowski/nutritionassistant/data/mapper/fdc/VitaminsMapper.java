package pl.polsl.wachowski.nutritionassistant.data.mapper.fdc;

import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;
import pl.polsl.wachowski.nutritionassistant.api.nutrients.Vitamin;

import java.util.HashMap;
import java.util.Map;

public class VitaminsMapper implements NutrientMapper {

    private static final Map<Integer, Vitamin> vitaminMap = new HashMap<>(Vitamin.values().length);

    private static final VitaminsMapper instance = new VitaminsMapper();

    public static VitaminsMapper getInstance() {
        return instance;
    }

    private VitaminsMapper() {
        vitaminMap.put(1104, Vitamin.A);
        vitaminMap.put(1165, Vitamin.B1);
        vitaminMap.put(1166, Vitamin.B2);
        vitaminMap.put(1167, Vitamin.B3);
        vitaminMap.put(1180, Vitamin.B4);
        vitaminMap.put(1170, Vitamin.B5);
        vitaminMap.put(1175, Vitamin.B6);
        vitaminMap.put(1178, Vitamin.B12);
        vitaminMap.put(1162, Vitamin.C);
        vitaminMap.put(1110, Vitamin.D);
        vitaminMap.put(1109, Vitamin.E);   //TODO only alpha-tocopherol
        vitaminMap.put(1177, Vitamin.FOLATE);
        vitaminMap.put(1185, Vitamin.K);
    }

    @Override
    public Vitamin get(final Integer id) {
        return vitaminMap.get(id);
    }

}
