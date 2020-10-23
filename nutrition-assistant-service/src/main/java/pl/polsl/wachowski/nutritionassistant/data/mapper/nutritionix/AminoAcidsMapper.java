package pl.polsl.wachowski.nutritionassistant.data.mapper.nutritionix;

import pl.polsl.wachowski.nutritionassistant.api.nutrients.AminoAcid;
import pl.polsl.wachowski.nutritionassistant.data.mapper.NutrientMapper;

import java.util.HashMap;
import java.util.Map;

public final class AminoAcidsMapper implements NutrientMapper {

    private static final Map<Integer, AminoAcid> aminoAcidMap = new HashMap<>(AminoAcid.values().length);

    private static final AminoAcidsMapper instance = new AminoAcidsMapper();

    public static AminoAcidsMapper getInstance() {
        return instance;
    }

    private AminoAcidsMapper() {
        aminoAcidMap.put(507, AminoAcid.CYSTEINE);
        aminoAcidMap.put(512, AminoAcid.HISTIDINE);
        aminoAcidMap.put(503, AminoAcid.ISOLEUCINE);
        aminoAcidMap.put(504, AminoAcid.LEUCINE);
        aminoAcidMap.put(505, AminoAcid.LYSINE);
        aminoAcidMap.put(506, AminoAcid.METHIONINE);
        aminoAcidMap.put(508, AminoAcid.PHENYLALANINE);
        aminoAcidMap.put(502, AminoAcid.THREONINE);
        aminoAcidMap.put(501, AminoAcid.TRYPTOPHAN);
        aminoAcidMap.put(509, AminoAcid.TYROSINE);
        aminoAcidMap.put(510, AminoAcid.VALINE);
    }

    @Override
    public AminoAcid get(final Integer id) {
        return aminoAcidMap.get(id);
    }

}
