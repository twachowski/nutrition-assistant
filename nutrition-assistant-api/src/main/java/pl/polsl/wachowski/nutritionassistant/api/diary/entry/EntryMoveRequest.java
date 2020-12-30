package pl.polsl.wachowski.nutritionassistant.api.diary.entry;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
public class EntryMoveRequest {

    @NotNull(message = "Previous position must not be null")
    @Min(value = 0, message = "Previous position must not be negative")
    Short previousPosition;

    @NotNull(message = "Current position must not be null")
    @Min(value = 0, message = "Current position must not be negative")
    Short currentPosition;

    public boolean isPositionUnchanged() {
        return previousPosition.equals(currentPosition);
    }

}
