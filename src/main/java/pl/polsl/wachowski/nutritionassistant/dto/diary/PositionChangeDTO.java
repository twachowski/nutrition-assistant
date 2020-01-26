package pl.polsl.wachowski.nutritionassistant.dto.diary;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
public class PositionChangeDTO {

    @NotNull(message = "Previous position must not be null")
    @Min(value = 0, message = "Previous position must not be negative")
    Short previousPosition;

    @NotNull(message = "Current position must not be null")
    @Min(value = 0, message = "Current position must not be negative")
    Short currentPosition;

    public boolean notChanged() {
        return previousPosition.equals(currentPosition);
    }

}
