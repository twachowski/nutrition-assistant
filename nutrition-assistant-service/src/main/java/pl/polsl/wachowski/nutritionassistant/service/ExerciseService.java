package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise.EditedExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.api.exercise.Exercise;
import pl.polsl.wachowski.nutritionassistant.api.exercise.ExerciseSearchResponse;
import pl.polsl.wachowski.nutritionassistant.data.provider.exercise.ExerciseDataProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.db.entry.ExerciseEntryEntity;
import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometricsEntity;
import pl.polsl.wachowski.nutritionassistant.exception.entry.EntryNotFoundException;
import pl.polsl.wachowski.nutritionassistant.repository.ExerciseRepository;

import java.util.List;
import java.util.Set;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseDataProviderAdapter provider;
    private final ProfileService profileService;

    @Autowired
    public ExerciseService(final ExerciseRepository exerciseRepository,
                           final ExerciseDataProviderAdapter provider,
                           final ProfileService profileService) {
        this.exerciseRepository = exerciseRepository;
        this.provider = provider;
        this.profileService = profileService;
    }

    public Set<Exercise> searchExercises(final String query) {
        final UserBiometricsEntity userBiometrics = profileService.getAuthenticatedUserBiometricsEntity();
        return provider.search(query, userBiometrics);
    }

    public ExerciseSearchResponse searchExercisesWithBiometrics(final String query) {
        final UserBiometricsEntity userBiometrics = profileService.getAuthenticatedUserBiometricsEntity();
        final Set<Exercise> exercises = provider.search(query, userBiometrics);
        return new ExerciseSearchResponse(ProfileService.toSimpleBiometrics(userBiometrics),
                                          exercises);
    }

    public void editExerciseEntry(final List<ExerciseEntryEntity> exerciseEntries,
                                  final short entryPosition,
                                  final EditedExerciseEntry editedExerciseEntry) {
        final ExerciseEntryEntity exerciseEntry = exerciseEntries.stream()
                .filter(entry -> entry.getPosition().equals(entryPosition))
                .findFirst()
                .orElseThrow(EntryNotFoundException::new);
        exerciseEntry.setAmount(editedExerciseEntry.getDuration());
        exerciseEntry.setTimeUnit(editedExerciseEntry.getTimeUnit());
        exerciseRepository.save(exerciseEntry);
    }

}
