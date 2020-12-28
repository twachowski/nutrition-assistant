package pl.polsl.wachowski.nutritionassistant.provider.exercise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.polsl.wachowski.nutritionassistant.api.exercise.Exercise;
import pl.polsl.wachowski.nutritionassistant.client.AlwaysFailingNutritionixClient;
import pl.polsl.wachowski.nutritionassistant.client.MockNutritionixClient;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserBiometricsEntity;
import pl.polsl.wachowski.nutritionassistant.exception.provider.NutritionixException;
import pl.polsl.wachowski.nutritionix.client.NutritionixClient;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NutritionixExerciseProviderAdapterTest {

    @Test
    @DisplayName("Should throw NutritionixException when exercise search result is failure")
    void shouldThrowNutritionixExceptionWhenExerciseSearchResultIsFailure() {
        //given
        final NutritionixClient nutritionixClient = new AlwaysFailingNutritionixClient();
        final NutritionixExerciseProviderAdapter provider = new NutritionixExerciseProviderAdapter(nutritionixClient);
        final Executable executable = () -> provider.searchExercises("running", UserBiometricsEntity.getDefault(null));

        //when

        //then
        assertThrows(NutritionixException.class, executable);
    }

    @Test
    @DisplayName("Should return set of exercises")
    void shouldReturnSetOfExercises() {
        //given
        final NutritionixClient nutritionixClient = new MockNutritionixClient();
        final NutritionixExerciseProviderAdapter provider = new NutritionixExerciseProviderAdapter(nutritionixClient);

        //when
        final Set<Exercise> exercises = provider.searchExercises("running", UserBiometricsEntity.getDefault(null));

        //then
        assertNotNull(exercises);
    }

}