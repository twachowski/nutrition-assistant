package pl.polsl.wachowski.nutritionassistant.util;

import pl.polsl.wachowski.nutritionassistant.api.food.NutrientDetails;
import pl.polsl.wachowski.nutritionassistant.api.nutrients.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class NutrientHelper {

    public static final Set<GeneralNutrient> MANDATORY_NUTRIENTS = EnumSet.of(GeneralNutrient.ENERGY,
                                                                              GeneralNutrient.TOTAL_CARBS,
                                                                              GeneralNutrient.TOTAL_FAT,
                                                                              GeneralNutrient.TOTAL_PROTEIN);
    public static final Map<String, Nutrient> NUTRIENT_NAME_MAP = Stream.of(AminoAcid.NAME_MAP,
                                                                            Carbohydrate.NAME_MAP,
                                                                            GeneralNutrient.NAME_MAP,
                                                                            Lipid.NAME_MAP,
                                                                            Mineral.NAME_MAP,
                                                                            Vitamin.NAME_MAP)
            .map(Map::entrySet)
            .flatMap(Set::stream)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

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

    public static Optional<Nutrient> fromName(final String nutrientName) {
        return Optional.ofNullable(NUTRIENT_NAME_MAP.get(nutrientName));
    }

    private static <T> Predicate<T> not(final Predicate<T> predicate) {
        return predicate.negate();
    }

    private static NutrientDetails toNutrientDetails(final Nutrient nutrient) {
        return new NutrientDetails(nutrient, 0.f);
    }

}
