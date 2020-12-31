package pl.polsl.wachowski.nutritionassistant.samples;

import pl.polsl.wachowski.nutritionassistant.api.diary.entry.EntryMoveRequest;

public final class EntryMoveRequestSamples {

    private EntryMoveRequestSamples() {
    }

    public static EntryMoveRequest validWithDifferentPositions() {
        return new EntryMoveRequest((short) 0, (short) 1);
    }

    public static EntryMoveRequest validWithSamePositions() {
        return new EntryMoveRequest((short) 0, (short) 0);
    }

    public static EntryMoveRequest withNullPreviousPosition() {
        return new EntryMoveRequest(null, (short) 0);
    }

    public static EntryMoveRequest withNegativePreviousPosition() {
        return new EntryMoveRequest((short) -1, (short) 0);
    }

    public static EntryMoveRequest withNullCurrentPosition() {
        return new EntryMoveRequest((short) 0, null);
    }

    public static EntryMoveRequest withNegativeCurrentPosition() {
        return new EntryMoveRequest((short) 0, (short) -1);
    }

}
