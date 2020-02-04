package pl.polsl.wachowski.nutritionassistant.dto.diary;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
public class EntryDeleteRequest {

    @NotBlank(message = "User must not be blank")
    String user;

    @NotNull(message = "Diary date must not be null")
    LocalDate diaryDate;

    @NotNull(message = "Entry position must not be null")
    @Min(value = 0, message = "Entry position must not be negative")
    Short entryPosition;

}