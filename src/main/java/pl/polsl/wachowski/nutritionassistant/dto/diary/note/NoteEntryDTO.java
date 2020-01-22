package pl.polsl.wachowski.nutritionassistant.dto.diary.note;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
public class NoteEntryDTO {

    @NotBlank(message = "Content must not be blank")
    @Size(
            min = 1,
            max = 255,
            message = "Content must be between 1 and 255 characters")
    String content;

    @NotNull(message = "Position must not be null")
    Short position;

}
