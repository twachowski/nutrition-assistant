package pl.polsl.wachowski.nutritionassistant.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pl.polsl.wachowski.nutritionassistant.samples.ExerciseSearchResponseSamples;
import pl.polsl.wachowski.nutritionassistant.service.ExerciseService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.polsl.wachowski.nutritionassistant.api.NutritionAssistantApi.EXERCISES_API_SUFFIX;
import static pl.polsl.wachowski.nutritionassistant.api.NutritionAssistantApi.QUERY;

@WebMvcTest(value = ExerciseController.class,
            excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {
        ExerciseController.class,
        ControllerExceptionHandler.class
})
class ExerciseControllerValidationTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ExerciseService exerciseService;

    @BeforeEach
    void setUp() {
        Mockito.when(exerciseService.searchExercisesWithBiometrics("run"))
                .thenReturn(ExerciseSearchResponseSamples.withRunningExercise());
    }

    @Test
    @DisplayName("Should fail validation when trying to search exercises with null query parameter")
    void shouldFailValidationWhenTryingToSearchExercisesWithNullQueryParameter() throws Exception {
        //given

        //when

        //then
        mvc.perform(get(EXERCISES_API_SUFFIX))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to search exercises with empty query parameter")
    void shouldFailValidationWhenTryingToSearchExercisesWithEmptyQueryParameter() throws Exception {
        //given
        final String query = "";

        //when

        //then
        mvc.perform(get(EXERCISES_API_SUFFIX)
                            .param(QUERY, query))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when trying to search exercises with blank query parameter")
    void shouldFailValidationWhenTryingToSearchExercisesWithBlankQueryParameter() throws Exception {
        //given
        final String query = "    ";

        //when

        //then
        mvc.perform(get(EXERCISES_API_SUFFIX)
                            .param(QUERY, query))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return biometrics and set of exercises")
    void shouldReturnBiometricsAndSetOfExercises() throws Exception {
        //given
        final String query = "run";

        //when

        //then
        mvc.perform(get(EXERCISES_API_SUFFIX)
                            .param(QUERY, query))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("userSimpleBiometrics").exists())
                .andExpect(jsonPath("exercises").isArray());
    }

}