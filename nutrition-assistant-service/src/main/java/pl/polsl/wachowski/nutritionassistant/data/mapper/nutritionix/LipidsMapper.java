package pl.polsl.wachowski.nutritionassistant.data.mapper.nutritionix;

import pl.polsl.wachowski.nutritionassistant.api.nutrients.Lipid;
import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LipidsMapper implements NutrientMapper {

    private static final Integer O6_PRIMARY_ID = 675;
    private static final Integer O6_SECONDARY_ID = 618;
    private static final Integer O3_ALA_PRIMARY_ID = 851;
    private static final Integer O3_ALA_SECONDARY_ID = 619;
    private static final Integer O3_DPA_ID = 631;
    private static final Integer O3_EPA_ID = 629;
    private static final Integer O3_DHA_ID = 621;

    public static final List<Integer> OMEGA6_IDS = Arrays.asList(O6_PRIMARY_ID, O6_SECONDARY_ID);
    public static final List<Integer> OMEGA3_ALA_IDS = Arrays.asList(O3_ALA_PRIMARY_ID, O3_ALA_SECONDARY_ID);
    public static final List<Integer> OMEGA3_NON_ALA_IDS = Arrays.asList(O3_DPA_ID, O3_EPA_ID, O3_DHA_ID);
    public static final List<Integer> OMEGA_ACIDS_IDS = Arrays.asList(
            O6_PRIMARY_ID,
            O6_SECONDARY_ID,
            O3_ALA_PRIMARY_ID,
            O3_ALA_SECONDARY_ID,
            O3_DPA_ID,
            O3_EPA_ID,
            O3_DHA_ID);

    private static final Map<Integer, Lipid> lipidMap = new HashMap<>(Lipid.values().length);

    private static final LipidsMapper instance = new LipidsMapper();

    public static LipidsMapper getInstance() {
        return instance;
    }

    private LipidsMapper() {
        lipidMap.put(606, Lipid.SATURATED_FAT);
        lipidMap.put(645, Lipid.MONOUNSATURATED_FAT);
        lipidMap.put(646, Lipid.POLYUNSATURATED_FAT);
        lipidMap.put(O6_PRIMARY_ID, Lipid.OMEGA6);
        lipidMap.put(O6_SECONDARY_ID, Lipid.OMEGA6);            //two mappings exist for LA
        lipidMap.put(O3_ALA_PRIMARY_ID, Lipid.OMEGA3_ALA);
        lipidMap.put(O3_ALA_SECONDARY_ID, Lipid.OMEGA3_ALA);    //two mappings exist for ALA
        lipidMap.put(O3_DPA_ID, Lipid.OMEGA3_DPA);
        lipidMap.put(O3_EPA_ID, Lipid.OMEGA3_EPA);
        lipidMap.put(O3_DHA_ID, Lipid.OMEGA3_DHA);
        lipidMap.put(605, Lipid.TRANS_FAT);
        lipidMap.put(601, Lipid.CHOLESTEROL);
    }

    @Override
    public Lipid get(final Integer id) {
        return lipidMap.get(id);
    }

}
