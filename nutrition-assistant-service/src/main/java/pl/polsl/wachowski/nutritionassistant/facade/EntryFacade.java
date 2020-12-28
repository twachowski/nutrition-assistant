package pl.polsl.wachowski.nutritionassistant.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise.EditedExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.EditedFoodEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.note.NoteEntry;
import pl.polsl.wachowski.nutritionassistant.api.exercise.Exercise;
import pl.polsl.wachowski.nutritionassistant.api.food.Food;
import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.ExerciseEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.FoodEntryEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.entry.NoteEntryEntity;
import pl.polsl.wachowski.nutritionassistant.service.ExerciseService;
import pl.polsl.wachowski.nutritionassistant.service.NoteService;

import java.util.List;
import java.util.Set;

@Component
public class EntryFacade {

    private final FoodFacade foodFacade;
    private final ExerciseService exerciseService;
    private final NoteService noteService;

    @Autowired
    public EntryFacade(final FoodFacade foodFacade,
                       final ExerciseService exerciseService,
                       final NoteService noteService) {
        this.foodFacade = foodFacade;
        this.exerciseService = exerciseService;
        this.noteService = noteService;
    }

    public Food getFood(final String externalId, final NutritionDataProvider provider) {
        return foodFacade.getFood(externalId, provider);
    }

    public Set<Exercise> searchExercises(final String query) {
        return exerciseService.searchExercises(query);
    }

    public void editFoodEntry(final List<FoodEntryEntity> foodEntries,
                              final short entryPosition,
                              final EditedFoodEntry editedFoodEntry) {
        foodFacade.editFoodEntry(foodEntries, entryPosition, editedFoodEntry);
    }

    public void editExerciseEntry(final List<ExerciseEntryEntity> exerciseEntries,
                                  final short entryPosition,
                                  final EditedExerciseEntry editedExerciseEntry) {
        exerciseService.editExerciseEntry(exerciseEntries, entryPosition, editedExerciseEntry);
    }

    public void editNoteEntry(final List<NoteEntryEntity> noteEntries,
                              final short entryPosition,
                              final NoteEntry editedNoteEntry) {
        noteService.editNoteEntry(noteEntries, entryPosition, editedNoteEntry);
    }

}
