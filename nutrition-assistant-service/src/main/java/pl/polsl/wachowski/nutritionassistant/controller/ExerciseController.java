package pl.polsl.wachowski.nutritionassistant.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.wachowski.nutritionassistant.api.exercise.ExerciseSearchResponse;
import pl.polsl.wachowski.nutritionassistant.service.ExerciseService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pl.polsl.wachowski.nutritionassistant.api.NutritionAssistantApi.EXERCISES_API_SUFFIX;
import static pl.polsl.wachowski.nutritionassistant.api.NutritionAssistantApi.QUERY;

@RestController
@RequestMapping(EXERCISES_API_SUFFIX)
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(final ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    ResponseEntity<ExerciseSearchResponse> searchExercises(@RequestParam(QUERY) final String query) throws MissingServletRequestParameterException {
        if (StringUtils.isBlank(query)) {
            throw new MissingServletRequestParameterException(QUERY, "String");
        }
        final ExerciseSearchResponse response = exerciseService.searchExercisesWithBiometrics(query);
        return ResponseEntity.ok(response);
    }

}
