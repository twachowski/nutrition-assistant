package pl.polsl.wachowski.nutritionassistant.samples;

import pl.polsl.wachowski.nutritionassistant.api.food.NutrientDetails;
import pl.polsl.wachowski.nutritionassistant.api.nutrients.*;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class NutrientDetailsSamples {

    public static final NutrientDetails ENERGY_NUTRIENT = new NutrientDetails(GeneralNutrient.ENERGY, 0.f);
    public static final NutrientDetails CARBS_NUTRIENT = new NutrientDetails(GeneralNutrient.TOTAL_CARBS, 0.f);
    public static final NutrientDetails FAT_NUTRIENT = new NutrientDetails(GeneralNutrient.TOTAL_FAT, 0.f);
    public static final NutrientDetails PROTEIN_NUTRIENT = new NutrientDetails(GeneralNutrient.TOTAL_PROTEIN, 0.f);

    private NutrientDetailsSamples() {
    }

    public static Stream<NutrientDetails> onlyMandatoryNutrientsWithZeroAmount() {
        return Stream.of(ENERGY_NUTRIENT,
                         CARBS_NUTRIENT,
                         FAT_NUTRIENT,
                         PROTEIN_NUTRIENT);
    }

    public static Stream<NutrientDetails> allNutrientsWithRandomAmounts() {
        final Supplier<Float> floatSupplier = new Random(System.currentTimeMillis())::nextFloat;
        return Stream.of(GeneralNutrient.values(),
                         Carbohydrate.values(),
                         AminoAcid.values(),
                         Lipid.values(),
                         Mineral.values(),
                         Vitamin.values())
                .flatMap(Stream::of)
                .map(nutrient -> new NutrientDetails(nutrient, floatSupplier.get()));
    }

}
