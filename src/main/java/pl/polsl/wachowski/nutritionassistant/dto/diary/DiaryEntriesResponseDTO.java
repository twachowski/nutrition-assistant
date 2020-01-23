package pl.polsl.wachowski.nutritionassistant.dto.diary;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.dto.diary.exercise.ExerciseEntryDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.food.FoodEntryDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.diary.note.NoteEntryDTO;

import java.util.Collections;
import java.util.List;

@Value
public class DiaryEntriesResponseDTO {

    List<FoodEntryDetailsDTO> foodEntries;

    List<ExerciseEntryDetailsDTO> exerciseEntries;

    List<NoteEntryDTO> noteEntries;

    public static DiaryEntriesResponseDTO empty() {
        return new DiaryEntriesResponseDTO(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
    }

}
