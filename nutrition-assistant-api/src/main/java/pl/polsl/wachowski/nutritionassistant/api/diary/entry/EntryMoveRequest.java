package pl.polsl.wachowski.nutritionassistant.api.diary.entry;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
public class EntryMoveRequest {

    @NotNull
    @Min(value = 0, message = "Previous position must not be negative")
    Short previousPosition;

    @NotNull
    @Min(value = 0, message = "Current position must not be negative")
    Short currentPosition;

    public boolean isPositionUchanged() {
        return previousPosition.equals(currentPosition);
    }
}
