package pl.polsl.wachowski.nutritionassistant.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.polsl.wachowski.nutritionassistant.api.food.NutrientDetails;
import pl.polsl.wachowski.nutritionassistant.api.nutrients.*;
import pl.polsl.wachowski.nutritionassistant.samples.NutrientDetailsSamples;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Test
    @DisplayName("Should return empty Optional if non-existing amino acid name is given")
    void shouldReturnEmptyOptionalIfNonExistingAminoAcidNameIsGiven() {
        //given
        final String aminoAcidName = "amino acid";

        //when
        final Optional<Nutrient> optionalNutrient = NutrientHelper.fromName(aminoAcidName);

        //then
        assertFalse(optionalNutrient.isPresent());
    }

    @Test
    @DisplayName("Should return empty Optional if non-existing carbohydrate name is given")
    void shouldReturnEmptyOptionalIfNonExistingCarbohydrateNameIsGiven() {
        //given
        final String carbohydrateName = "carb";

        //when
        final Optional<Nutrient> optionalNutrient = NutrientHelper.fromName(carbohydrateName);

        //then
        assertFalse(optionalNutrient.isPresent());
    }

    @Test
    @DisplayName("Should return empty Optional if non-existing general nutrient name is given")
    void shouldReturnEmptyOptionalIfNonExistingGeneralNutrientNameIsGiven() {
        //given
        final String generalNutrientName = "general nutrient";

        //when
        final Optional<Nutrient> optionalNutrient = NutrientHelper.fromName(generalNutrientName);

        //then
        assertFalse(optionalNutrient.isPresent());
    }

    @Test
    @DisplayName("Should return empty Optional if non-existing lipid name is given")
    void shouldReturnEmptyOptionalIfNonExistingLipidNameIsGiven() {
        //given
        final String lipidName = "lipid";

        //when
        final Optional<Nutrient> optionalNutrient = NutrientHelper.fromName(lipidName);

        //then
        assertFalse(optionalNutrient.isPresent());
    }

    @Test
    @DisplayName("Should return empty Optional if non-existing mineral name is given")
    void shouldReturnEmptyOptionalIfNonExistingMineralNameIsGiven() {
        //given
        final String mineralName = "mineral";

        //when
        final Optional<Nutrient> optionalNutrient = NutrientHelper.fromName(mineralName);

        //then
        assertFalse(optionalNutrient.isPresent());
    }

    @Test
    @DisplayName("Should return empty Optional if non-existing vitamin name is given")
    void shouldReturnEmptyOptionalIfNonExistingVitaminNameIsGiven() {
        //given
        final String vitaminName = "vitamin";

        //when
        final Optional<Nutrient> optionalNutrient = NutrientHelper.fromName(vitaminName);

        //then
        assertFalse(optionalNutrient.isPresent());
    }

    @Test
    @DisplayName("Should return amino acid")
    void shouldReturnAminoAcid() {
        //given
        final String aminoAcidName = "LEUCINE";

        //when
        final Nutrient nutrient = NutrientHelper.fromName(aminoAcidName)
                .orElseThrow(IllegalStateException::new);

        //then
        assertSame(AminoAcid.LEUCINE, nutrient);
    }

    @Test
    @DisplayName("Should return carbohydrate")
    void shouldReturnCarbohydrate() {
        //given
        final String carbohydrateName = "STARCH";

        //when
        final Nutrient nutrient = NutrientHelper.fromName(carbohydrateName)
                .orElseThrow(IllegalStateException::new);

        //then
        assertSame(Carbohydrate.STARCH, nutrient);
    }

    @Test
    @DisplayName("Should return general nutrient")
    void shouldReturnGeneralNutrient() {
        //given
        final String generalNutrientName = "ALCOHOL";

        //when
        final Nutrient nutrient = NutrientHelper.fromName(generalNutrientName)
                .orElseThrow(IllegalStateException::new);

        //then
        assertSame(GeneralNutrient.ALCOHOL, nutrient);
    }

    @Test
    @DisplayName("Should return lipid")
    void shouldReturnLipid() {
        //given
        final String lipidName = "CHOLESTEROL";

        //when
        final Nutrient nutrient = NutrientHelper.fromName(lipidName)
                .orElseThrow(IllegalStateException::new);

        //then
        assertSame(Lipid.CHOLESTEROL, nutrient);
    }

    @Test
    @DisplayName("Should return mineral")
    void shouldReturnMineral() {
        //given
        final String mineralName = "SODIUM";

        //when
        final Nutrient nutrient = NutrientHelper.fromName(mineralName)
                .orElseThrow(IllegalStateException::new);

        //then
        assertSame(Mineral.SODIUM, nutrient);
    }

    @Test
    @DisplayName("Should return vitamin")
    void shouldReturnVitamin() {
        //given
        final String vitaminName = "B12";

        //when
        final Nutrient nutrient = NutrientHelper.fromName(vitaminName)
                .orElseThrow(IllegalStateException::new);

        //then
        assertSame(Vitamin.B12, nutrient);
    }

}