package pl.polsl.wachowski.nutritionassistant.controller.diary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.wachowski.nutritionassistant.dto.diary.DiaryEntriesRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.DiaryEntriesResponseDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.exercise.NewExerciseEntryRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.food.NewFoodEntryRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.note.NewNoteEntryRequestDTO;
import pl.polsl.wachowski.nutritionassistant.service.DiaryService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @Autowired
    public DiaryController(final DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDiaryEntries(@RequestBody @Valid final DiaryEntriesRequestDTO request) {
        final DiaryEntriesResponseDTO response = diaryService.getDiaryEntries(request.getUser(), request.getDiaryDate());
        return ResponseEntity.ok(response);
    }

    @RequestMapping(
            path = "/add/food",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addFood(@RequestBody @Valid final NewFoodEntryRequestDTO request) {
        diaryService.addFoodEntry(request);

        return ResponseEntity
                .ok()
                .build();
    }

    @RequestMapping(
            path = "/add/exercise",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addExercise(@RequestBody @Valid final NewExerciseEntryRequestDTO request) {
        diaryService.addExerciseEntry(request);

        return ResponseEntity
                .ok()
                .build();
    }

    @RequestMapping(
            path = "/add/note",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addNote(@RequestBody @Valid final NewNoteEntryRequestDTO request) {
        diaryService.addNoteEntry(request);

        return ResponseEntity
                .ok()
                .build();
    }

}
