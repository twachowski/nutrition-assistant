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
    public ResponseEntity<DiaryEntriesResponseDTO> getDiaryEntries(@RequestBody @Valid final DiaryEntriesRequestDTO request) {
        final DiaryEntriesResponseDTO response = diaryService.getDiaryEntries(request.getDiaryDate());
        return ResponseEntity.ok(response);
    }

    @RequestMapping(
            path = "/add/food",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addFood(@RequestBody @Valid final NewFoodEntryRequestDTO request) {
        diaryService.addFoodEntry(request.getDiaryDate(), request.getFoodEntry());
    }

    @RequestMapping(
            path = "/add/exercise",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addExercise(@RequestBody @Valid final NewExerciseEntryRequestDTO request) {
        diaryService.addExerciseEntry(request.getDiaryDate(), request.getExerciseEntry());
    }

    @RequestMapping(
            path = "/add/note",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addNote(@RequestBody @Valid final NewNoteEntryRequestDTO request) {
        diaryService.addNoteEntry(request.getDiaryDate(), request.getNoteEntry());
    }

    @RequestMapping(
            path = "/edit/food",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void editFood(@RequestBody @Valid final EntryEditRequestDTO<EditedFoodEntryDTO> request) {
        diaryService.editFoodEntry(request.getDiaryDate(), request.getEditedEntry());
    }

    @RequestMapping(
            path = "/edit/exercise",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void editExercise(@RequestBody @Valid final EntryEditRequestDTO<EditedExerciseEntryDTO> request) {
        diaryService.editExerciseEntry(request.getDiaryDate(), request.getEditedEntry());
    }

    @RequestMapping(
            path = "/edit/note",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void editNote(@RequestBody @Valid final EntryEditRequestDTO<NoteEntryDTO> request) {
        diaryService.editNoteEntry(request.getDiaryDate(), request.getEditedEntry());
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
