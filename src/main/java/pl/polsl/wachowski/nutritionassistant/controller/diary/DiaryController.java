package pl.polsl.wachowski.nutritionassistant.controller.diary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.wachowski.nutritionassistant.dto.diary.*;
import pl.polsl.wachowski.nutritionassistant.dto.diary.exercise.EditedExerciseEntryDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.exercise.NewExerciseEntryRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.food.EditedFoodEntryDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.food.NewFoodEntryRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.note.NewNoteEntryRequestDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.note.NoteEntryDTO;
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
        final DiaryEntriesResponseDTO response = diaryService.getDiaryEntries(request.getDiaryDate());
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

    @RequestMapping(
            path = "/edit/food",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editFood(@RequestBody @Valid final EntryEditRequest<EditedFoodEntryDTO> request) {
        diaryService.editFoodEntry(request.getDiaryDate(), request.getEditedEntry());

        return ResponseEntity
                .ok()
                .build();
    }

    @RequestMapping(
            path = "/edit/exercise",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editExercise(@RequestBody @Valid final EntryEditRequest<EditedExerciseEntryDTO> request) {
        diaryService.editExerciseEntry(request.getDiaryDate(), request.getEditedEntry());

        return ResponseEntity
                .ok()
                .build();
    }

    @RequestMapping(
            path = "/edit/note",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editNote(@RequestBody @Valid final EntryEditRequest<NoteEntryDTO> request) {
        diaryService.editNoteEntry(request.getDiaryDate(), request.getEditedEntry());

        return ResponseEntity
                .ok()
                .build();
    }

    @RequestMapping(
            path = "/delete",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteEntry(@RequestBody @Valid final EntryDeleteRequest request) {
        diaryService.deleteEntry(request.getDiaryDate(), request.getEntryPosition());

        return ResponseEntity
                .ok()
                .build();
    }

    @RequestMapping(
            path = "/reorder",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity reorder(@RequestBody @Valid final ReorderRequestDTO request) {
        diaryService.reorder(request.getDiaryDate(), request.getPositionChange());

        return ResponseEntity
                .ok()
                .build();
    }

}
