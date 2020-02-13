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
import pl.polsl.wachowski.nutritionassistant.dto.diary.exercise.ExerciseEntryDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.food.EditedFoodEntryDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.food.FoodEntryDTO;
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
    public ResponseEntity<DiaryEntriesResponseDTO> getDiaryEntries(@RequestBody @Valid final DiaryEntriesRequestDTO request) {
        final DiaryEntriesResponseDTO response = diaryService.getDiaryEntries(request.getDiaryDate());
        return ResponseEntity.ok(response);
    }

    @RequestMapping(
            path = "/add/food",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addFood(@RequestBody @Valid final EntryRequestDTO<FoodEntryDTO> request) {
        diaryService.addFoodEntry(request.getDiaryDate(), request.getEntry());
    }

    @RequestMapping(
            path = "/add/exercise",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addExercise(@RequestBody @Valid final EntryRequestDTO<ExerciseEntryDTO> request) {
        diaryService.addExerciseEntry(request.getDiaryDate(), request.getEntry());
    }

    @RequestMapping(
            path = "/add/note",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addNote(@RequestBody @Valid final EntryRequestDTO<NoteEntryDTO> request) {
        diaryService.addNoteEntry(request.getDiaryDate(), request.getEntry());
    }

    @RequestMapping(
            path = "/edit/food",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void editFood(@RequestBody @Valid final EntryRequestDTO<EditedFoodEntryDTO> request) {
        diaryService.editFoodEntry(request.getDiaryDate(), request.getEntry());
    }

    @RequestMapping(
            path = "/edit/exercise",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void editExercise(@RequestBody @Valid final EntryRequestDTO<EditedExerciseEntryDTO> request) {
        diaryService.editExerciseEntry(request.getDiaryDate(), request.getEntry());
    }

    @RequestMapping(
            path = "/edit/note",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void editNote(@RequestBody @Valid final EntryRequestDTO<NoteEntryDTO> request) {
        diaryService.editNoteEntry(request.getDiaryDate(), request.getEntry());
    }

    @RequestMapping(
            path = "/delete",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteEntry(@RequestBody @Valid final EntryDeleteRequestDTO request) {
        diaryService.deleteEntry(request.getDiaryDate(), request.getEntryPosition());
    }

    @RequestMapping(
            path = "/reorder",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void reorder(@RequestBody @Valid final ReorderRequestDTO request) {
        diaryService.reorder(request.getDiaryDate(), request.getPositionChange());
    }

}
