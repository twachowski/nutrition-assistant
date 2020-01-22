package pl.polsl.wachowski.nutritionassistant.dto.diary.note;

import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
public class NewNoteEntryRequestDTO {

    @NotBlank(message = "User must not be blank")
    String user;

    @NotNull(message = "Diary date must not be null")
    LocalDate diaryDate;

    @NotNull(message = "Note entry must not be null")
    @Valid
    NoteEntryDTO noteEntry;

}
