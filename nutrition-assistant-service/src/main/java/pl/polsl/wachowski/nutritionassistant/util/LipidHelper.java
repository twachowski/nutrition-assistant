package pl.polsl.wachowski.nutritionassistant.util;

import pl.polsl.wachowski.nutritionassistant.data.ExternalNutrientDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public final class LipidHelper {

    private final List<Integer> omega6Ids;
    private final List<Integer> omega3AlaIds;
    private final List<Integer> omega3NonAlaIds;

    public LipidHelper(final List<Integer> omega6Ids,
                       final List<Integer> omega3AlaIds,
                       final List<Integer> omega3NonAlaIds) {
        this.omega6Ids = omega6Ids;
        this.omega3AlaIds = omega3AlaIds;
        this.omega3NonAlaIds = omega3NonAlaIds;
    }

    public float getTotalOmega6Amount(final Collection<? extends ExternalNutrientDetails> nutrients) {
        final ExternalNutrientDetails nutrient = nutrients.stream()
                .filter(n -> omega6Ids.contains(n.getId()))
                .reduce(null, this::getPrecedingO6, this::getPrecedingO6);
        return Optional.ofNullable(nutrient)
                .map(ExternalNutrientDetails::getAmount)
                .orElse(0.f);
    }

    public float getTotalOmega3Amount(final Collection<? extends ExternalNutrientDetails> nutrients) {
        final float alaAmount = getOmega3ALA(nutrients);
        final float nonAlaAmount = nutrients.stream()
                .filter(n -> omega3NonAlaIds.contains(n.getId()))
                .map(ExternalNutrientDetails::getAmount)
                .reduce(Float::sum)
                .orElse(0.f);
        return alaAmount + nonAlaAmount;
    }

    private float getOmega3ALA(final Collection<? extends ExternalNutrientDetails> nutrients) {
        final ExternalNutrientDetails nutrient = nutrients.stream()
                .filter(n -> omega3AlaIds.contains(n.getId()))
                .reduce(null, this::getPrecedingO3ALA, this::getPrecedingO3ALA);
        return Optional.ofNullable(nutrient)
                .map(ExternalNutrientDetails::getAmount)
                .orElse(0.f);
    }

    private ExternalNutrientDetails getPrecedingO6(final ExternalNutrientDetails n1,
                                                   final ExternalNutrientDetails n2) {
        return getPrecedingNutrient(n1, n2, omega6Ids);
    }

    private ExternalNutrientDetails getPrecedingO3ALA(final ExternalNutrientDetails n1,
                                                      final ExternalNutrientDetails n2) {
        return getPrecedingNutrient(n1, n2, omega3AlaIds);
    }

    private static ExternalNutrientDetails getPrecedingNutrient(final ExternalNutrientDetails n1,
                                                                final ExternalNutrientDetails n2,
                                                                final List<Integer> nutrientIds) {
        if (n1 == null)
            return n2;
        else if (n2 == null)
            return n1;
        return nutrientIds.indexOf(n1.getId()) < nutrientIds.indexOf(n2.getId()) ? n1 : n2;
    }

}
