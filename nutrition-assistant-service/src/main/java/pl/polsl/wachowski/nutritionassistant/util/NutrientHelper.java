package pl.polsl.wachowski.nutritionassistant.util;

import pl.polsl.wachowski.nutritionassistant.api.food.NutrientDetails;
import pl.polsl.wachowski.nutritionassistant.api.nutrients.GeneralNutrient;
import pl.polsl.wachowski.nutritionassistant.api.nutrients.Nutrient;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class NutrientHelper {

    private static final Set<GeneralNutrient> MANDATORY_NUTRIENTS = EnumSet.of(GeneralNutrient.ENERGY,
                                                                               GeneralNutrient.TOTAL_CARBS,
                                                                               GeneralNutrient.TOTAL_FAT,
                                                                               GeneralNutrient.TOTAL_PROTEIN);

    private NutrientHelper() {
    }

    public static void addMandatoryNutrientsIfMissing(final Collection<NutrientDetails> nutrientDetails) {
        final Set<Nutrient> nutrients = nutrientDetails.stream()
                .map(NutrientDetails::getNutrient)
                .collect(Collectors.toSet());
        MANDATORY_NUTRIENTS.stream()
                .filter(not(nutrients::contains))
                .map(NutrientHelper::toNutrientDetails)
                .forEach(nutrientDetails::add);
    }

    private static <T> Predicate<T> not(final Predicate<T> predicate) {
        return predicate.negate();
    }

    private static NutrientDetails toNutrientDetails(final Nutrient nutrient) {
        return new NutrientDetails(nutrient, 0.f);
    }

}
