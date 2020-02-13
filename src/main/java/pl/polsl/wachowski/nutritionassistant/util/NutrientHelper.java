package pl.polsl.wachowski.nutritionassistant.util;

import pl.polsl.wachowski.nutritionassistant.def.nutrition.GeneralNutrient;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.Nutrient;
import pl.polsl.wachowski.nutritionassistant.dto.details.NutrientDetailDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class NutrientHelper {

    private static final List<Nutrient> mandatoryNutrients = Arrays.asList(
            GeneralNutrient.ENERGY,
            GeneralNutrient.TOTAL_CARBS,
            GeneralNutrient.TOTAL_FAT,
            GeneralNutrient.TOTAL_PROTEIN);

    private NutrientHelper() {
    }

    public static void addMandatoryNutrientsIfMissing(final List<NutrientDetailDTO> nutrients) {
        final List<Nutrient> missingNutrients = new ArrayList<>(mandatoryNutrients);
        nutrients
                .stream()
                .map(NutrientDetailDTO::getNutrient)
                .filter(mandatoryNutrients::contains)
                .forEach(missingNutrients::remove);
        missingNutrients
                .stream()
                .map(NutrientHelper::toNutrientDetail)
                .forEach(nutrients::add);
    }

    private static NutrientDetailDTO toNutrientDetail(final Nutrient nutrient) {
        return new NutrientDetailDTO(nutrient, 0.f);
    }

}
