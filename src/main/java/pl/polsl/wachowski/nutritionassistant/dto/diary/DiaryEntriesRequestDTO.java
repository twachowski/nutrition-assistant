package pl.polsl.wachowski.nutritionassistant.dto.diary;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
public class DiaryEntriesRequestDTO {

    @NotNull(message = "Diary date must not be null")
    LocalDate diaryDate;

    @JsonCreator
    public DiaryEntriesRequestDTO(final LocalDate diaryDate) {
        this.diaryDate = diaryDate;
    }

}
