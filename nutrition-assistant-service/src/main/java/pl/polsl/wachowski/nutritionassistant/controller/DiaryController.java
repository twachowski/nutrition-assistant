package pl.polsl.wachowski.nutritionassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.wachowski.nutritionassistant.api.diary.DiaryEntriesResponse;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.EntryEditRequest;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.EntryMoveRequest;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.NewEntryRequest;
import pl.polsl.wachowski.nutritionassistant.facade.DiaryFacade;

import javax.validation.Valid;
import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pl.polsl.wachowski.nutritionassistant.api.NutritionAssistantApi.*;

@RestController
@RequestMapping(DIARY_DAY_API_SUFFIX)
public class DiaryController {

    private final DiaryFacade diaryFacade;

    @Autowired
    public DiaryController(final DiaryFacade diaryFacade) {
        this.diaryFacade = diaryFacade;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    ResponseEntity<DiaryEntriesResponse> getEntries(@PathVariable(DATE)
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate diaryDate) {
        final DiaryEntriesResponse response = diaryFacade.getEntries(diaryDate);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = ENTRIES_API_SUFFIX,
                 consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> addEntry(@PathVariable(DATE)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate diaryDate,
                                  @RequestBody @Valid final NewEntryRequest request) {
        diaryFacade.addEntry(diaryDate, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PatchMapping(path = ENTRIES_POSITION_API_SUFFIX,
                  consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> editEntry(@PathVariable(DATE)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate diaryDate,
                                   @PathVariable(ENTRY_POSITION) final short entryPosition,
                                   @RequestBody @Valid final EntryEditRequest request) {
        diaryFacade.editEntry(diaryDate, entryPosition, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = ENTRIES_POSITION_API_SUFFIX)
    ResponseEntity<Void> deleteEntry(@PathVariable(DATE)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate diaryDate,
                                     @PathVariable(ENTRY_POSITION) final short entryPosition) {
        diaryFacade.deleteEntry(diaryDate, entryPosition);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = ENTRIES_API_SUFFIX,
                  consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> moveEntry(@PathVariable(DATE)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate diaryDate,
                                   @RequestBody @Valid final EntryMoveRequest request) {
        diaryFacade.moveEntry(diaryDate, request);
        return ResponseEntity.noContent().build();
    }

}
