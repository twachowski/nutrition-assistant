package pl.polsl.wachowski.nutritionassistant.data.mapper.fdc;

import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.AminoAcid;

import java.util.HashMap;
import java.util.Map;

public class AminoAcidsMapper implements NutrientMapper {

    private static final Map<Integer, AminoAcid> aminoAcidMap = new HashMap<>(AminoAcid.values().length);

    private static final AminoAcidsMapper instance = new AminoAcidsMapper();

    public static AminoAcidsMapper getInstance() {
        return instance;
    }

    private AminoAcidsMapper() {
        aminoAcidMap.put(1216, AminoAcid.CYSTEINE);
        aminoAcidMap.put(1221, AminoAcid.HISTIDINE);
        aminoAcidMap.put(1212, AminoAcid.ISOLEUCINE);
        aminoAcidMap.put(1213, AminoAcid.LEUCINE);
        aminoAcidMap.put(1214, AminoAcid.LYSINE);
        aminoAcidMap.put(1215, AminoAcid.METHIONINE);
        aminoAcidMap.put(1217, AminoAcid.PHENYLALANINE);
        aminoAcidMap.put(1211, AminoAcid.THREONINE);
        aminoAcidMap.put(1210, AminoAcid.TRYPTOPHAN);
        aminoAcidMap.put(1218, AminoAcid.TYROSINE);
        aminoAcidMap.put(1219, AminoAcid.VALINE);
    }

    @Override
    public AminoAcid get(final Integer id) {
        return aminoAcidMap.get(id);
    }

}
