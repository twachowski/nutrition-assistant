package pl.polsl.wachowski.nutritionassistant.controller.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.ExerciseSearchRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.ExerciseSearchResponseDTO;
import pl.polsl.wachowski.nutritionassistant.service.ExerciseService;

import javax.validation.Valid;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(final ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @RequestMapping(
            path = "/search",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity search(@RequestBody @Valid final ExerciseSearchRequestDTO request) {
        final ExerciseSearchResponseDTO response = exerciseService.search(request.getQuery());
        return ResponseEntity.ok(response);
    }

}
