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

    public static HighlightedTargetsRequest withNullTarget1() {
        return new HighlightedTargetsRequest(null,
                                             AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             Mineral.CALCIUM.getName(),
                                             Vitamin.FOLATE.getName());
    }

    public static HighlightedTargetsRequest withEmptyTarget1() {
        return new HighlightedTargetsRequest("",
                                             AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             Mineral.CALCIUM.getName(),
                                             Vitamin.FOLATE.getName());
    }

    public static HighlightedTargetsRequest withBlankTarget1() {
        return new HighlightedTargetsRequest("   ",
                                             AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             Mineral.CALCIUM.getName(),
                                             Vitamin.FOLATE.getName());
    }

    public static HighlightedTargetsRequest withNullTarget2() {
        return new HighlightedTargetsRequest(AminoAcid.CYSTEINE.getName(),
                                             null,
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             Mineral.CALCIUM.getName(),
                                             Vitamin.FOLATE.getName());
    }

    public static HighlightedTargetsRequest withEmptyTarget2() {
        return new HighlightedTargetsRequest(AminoAcid.CYSTEINE.getName(),
                                             "",
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             Mineral.CALCIUM.getName(),
                                             Vitamin.FOLATE.getName());
    }

    public static HighlightedTargetsRequest withBlankTarget2() {
        return new HighlightedTargetsRequest(AminoAcid.CYSTEINE.getName(),
                                             "   ",
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             Mineral.CALCIUM.getName(),
                                             Vitamin.FOLATE.getName());
    }

    public static HighlightedTargetsRequest withNullTarget3() {
        return new HighlightedTargetsRequest(AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             null,
                                             Carbohydrate.STARCH.getName(),
                                             Mineral.CALCIUM.getName(),
                                             Vitamin.FOLATE.getName());
    }

    public static HighlightedTargetsRequest withEmptyTarget3() {
        return new HighlightedTargetsRequest(AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             "",
                                             Carbohydrate.STARCH.getName(),
                                             Mineral.CALCIUM.getName(),
                                             Vitamin.FOLATE.getName());
    }

    public static HighlightedTargetsRequest withBlankTarget3() {
        return new HighlightedTargetsRequest(AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             "   ",
                                             Carbohydrate.STARCH.getName(),
                                             Mineral.CALCIUM.getName(),
                                             Vitamin.FOLATE.getName());
    }

    public static HighlightedTargetsRequest withNullTarget4() {
        return new HighlightedTargetsRequest(AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             null,
                                             Mineral.CALCIUM.getName(),
                                             Vitamin.FOLATE.getName());
    }

    public static HighlightedTargetsRequest withEmptyTarget4() {
        return new HighlightedTargetsRequest(AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             "",
                                             Mineral.CALCIUM.getName(),
                                             Vitamin.FOLATE.getName());
    }

    public static HighlightedTargetsRequest withBlankTarget4() {
        return new HighlightedTargetsRequest(AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             "   ",
                                             Mineral.CALCIUM.getName(),
                                             Vitamin.FOLATE.getName());
    }

    public static HighlightedTargetsRequest withNullTarget5() {
        return new HighlightedTargetsRequest(AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             Mineral.CALCIUM.getName(),
                                             null,
                                             Vitamin.FOLATE.getName());
    }

    public static HighlightedTargetsRequest withEmptyTarget5() {
        return new HighlightedTargetsRequest(AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             Mineral.CALCIUM.getName(),
                                             "",
                                             Vitamin.FOLATE.getName());
    }

    public static HighlightedTargetsRequest withBlankTarget5() {
        return new HighlightedTargetsRequest(AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             Mineral.CALCIUM.getName(),
                                             "   ",
                                             Vitamin.FOLATE.getName());
    }

    public static HighlightedTargetsRequest withNullTarget6() {
        return new HighlightedTargetsRequest(AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             Mineral.CALCIUM.getName(),
                                             Vitamin.FOLATE.getName(),
                                             null);
    }

    public static HighlightedTargetsRequest withEmptyTarget6() {
        return new HighlightedTargetsRequest(AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             Mineral.CALCIUM.getName(),
                                             Vitamin.FOLATE.getName(),
                                             "");
    }

    public static HighlightedTargetsRequest withBlankTarget6() {
        return new HighlightedTargetsRequest(AminoAcid.CYSTEINE.getName(),
                                             Lipid.MONOUNSATURATED_FAT.getName(),
                                             Carbohydrate.STARCH.getName(),
                                             Mineral.CALCIUM.getName(),
                                             Vitamin.FOLATE.getName(),
                                             "   ");
    }

}
