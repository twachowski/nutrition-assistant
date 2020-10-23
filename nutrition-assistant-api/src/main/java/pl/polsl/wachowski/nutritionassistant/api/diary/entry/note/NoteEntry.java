package pl.polsl.wachowski.nutritionassistant.api.diary.entry.note;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
public class NoteEntry {

    @NotBlank
    @Size(min = 1,
          max = 255,
          message = "Note content length must be between 1 and 255 characters")
    String content;

}
