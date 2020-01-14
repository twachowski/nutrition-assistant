package pl.polsl.wachowski.nutritionassistant.data.mapper.nutritionix;

import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.Lipid;

import java.util.HashMap;
import java.util.Map;

public final class LipidsMapper implements NutrientMapper {

    private static final Map<Integer, Lipid> lipidMap = new HashMap<>(Lipid.values().length);

    private static final LipidsMapper instance = new LipidsMapper();

    public static LipidsMapper getInstance() {
        return instance;
    }

    private LipidsMapper() {
        lipidMap.put(606, Lipid.SATURATED_FAT);
        lipidMap.put(645, Lipid.MONOUNSATURATED_FAT);
        lipidMap.put(646, Lipid.POLYUNSATURATED_FAT);
//        lipidMap.put(606, Lipid.OMEGA6);    //LA + GLA + AA/ARA
//        lipidMap.put(606, Lipid.OMEGA3);    //ALA + EPA + DHA
        lipidMap.put(605, Lipid.TRANS_FAT);
        lipidMap.put(601, Lipid.CHOLESTEROL);
    }

    @Override
    public Lipid get(final Integer id) {
        return lipidMap.get(id);
    }

}
