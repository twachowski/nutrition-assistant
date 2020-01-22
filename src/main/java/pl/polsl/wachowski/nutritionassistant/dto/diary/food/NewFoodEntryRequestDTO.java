package pl.polsl.wachowski.nutritionassistant.dto.diary.food;

import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
public class NewFoodEntryRequestDTO {

    @NotBlank(message = "User must not be blank")
    String user;

    @NotNull(message = "Diary date must not be null")
    LocalDate diaryDate;

    @NotNull(message = "Food entry must not be null")
    @Valid
    FoodEntryDTO foodEntry;

}
