package pl.polsl.wachowski.nutritionassistant.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.polsl.wachowski.nutritionassistant.api.food.NutrientDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class NutrientHelperTest {

    @Test
    @DisplayName("Should add mandatory nutrients with zero amount to empty list of nutrients")
    void shouldAddGeneralNutrientsWithZeroAmountToEmptyListOfNutrients() {
        //given
        final List<NutrientDetails> nutrients = new ArrayList<>(0);
        final Set<NutrientDetails> mandatoryNutrientsWithZeroAmount = NutrientDetailsSamples.onlyMandatoryNutrientsWithZeroAmount()
                .collect(Collectors.toSet());

        //when
        NutrientHelper.addMandatoryNutrientsIfMissing(nutrients);

        //then
        assertEquals(nutrients.size(), mandatoryNutrientsWithZeroAmount.size());
        assertTrue(nutrients.containsAll(mandatoryNutrientsWithZeroAmount));
    }

    @Test
    @DisplayName("Should not modify collection of all nutrients")
    void shouldNotModifyCollectionOfAllNutrients() {
        //given
        final List<NutrientDetails> allNutrients = NutrientDetailsSamples.allNutrientsWithRandomAmounts()
                .collect(Collectors.toList());
        final List<NutrientDetails> nutrientsToModify = new ArrayList<>(allNutrients);

        //when
        NutrientHelper.addMandatoryNutrientsIfMissing(nutrientsToModify);

        //then
        assertIterableEquals(allNutrients, nutrientsToModify);
    }

}