package pl.polsl.wachowski.nutritionassistant.samples;

import pl.polsl.wachowski.nutritionassistant.api.nutrients.*;
import pl.polsl.wachowski.nutritionassistant.api.targets.HighlightedTargetsRequest;

public final class HighlightedTargetsRequestSamples {

    private HighlightedTargetsRequestSamples() {
    }

    public static HighlightedTargetsRequest request() {
        return new HighlightedTargetsRequest(GeneralNutrient.ENERGY.getName(),
                                             AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             Mineral.CALCIUM.getName(),
                                             Vitamin.FOLATE.getName());
    }

    public static HighlightedTargetsRequest withNullNutrient() {
        return new HighlightedTargetsRequest(GeneralNutrient.ENERGY.getName(),
                                             AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             null,
                                             Vitamin.FOLATE.getName());
    }

    public static HighlightedTargetsRequest withNonExistingNutrient() {
        return new HighlightedTargetsRequest(GeneralNutrient.ENERGY.getName(),
                                             AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             "Non-existing nutrient",
                                             Vitamin.FOLATE.getName());
    }

}
