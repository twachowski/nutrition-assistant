package pl.polsl.wachowski.nutritionassistant.samples;

import pl.polsl.wachowski.nutritionassistant.api.nutrients.*;
import pl.polsl.wachowski.nutritionassistant.api.targets.HighlightedTargetsResponse;

public final class HighlightedTargetsResponseSamples {

    private HighlightedTargetsResponseSamples() {
    }

    public static HighlightedTargetsResponse targets() {
        return new HighlightedTargetsResponse(GeneralNutrient.ALCOHOL,
                                              AminoAcid.TRYPTOPHAN,
                                              Mineral.ZINC,
                                              Vitamin.B12,
                                              Carbohydrate.SUGAR,
                                              Mineral.FLUORIDE);
    }

}
