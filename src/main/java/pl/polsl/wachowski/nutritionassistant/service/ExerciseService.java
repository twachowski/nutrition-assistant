package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.data.provider.exercise.ExerciseDataProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.db.entry.ExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.db.user.UserBiometrics;
import pl.polsl.wachowski.nutritionassistant.dto.diary.exercise.EditedExerciseEntryDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.ExerciseDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.ExerciseSearchResponseDTO;
import pl.polsl.wachowski.nutritionassistant.dto.user.UserSimpleBiometricsDTO;
import pl.polsl.wachowski.nutritionassistant.exception.entry.EntryNotFoundException;
import pl.polsl.wachowski.nutritionassistant.repository.ExerciseRepository;
import pl.polsl.wachowski.nutritionassistant.util.DateUtil;

import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    private final ExerciseDataProviderAdapter provider;

    private final UserService userService;

    @Autowired
    public ExerciseService(final ExerciseRepository exerciseRepository,
                           final ExerciseDataProviderAdapter provider,
                           final UserService userService) {
        this.exerciseRepository = exerciseRepository;
        this.provider = provider;
        this.userService = userService;
    }

    public ExerciseSearchResponseDTO search(final String query) {
        final String user = userService.getAuthenticatedUser();
        final UserBiometrics userBiometrics = userService.getUserBiometrics(user);
        final List<ExerciseDetailsDTO> exercises = provider.search(query, userBiometrics);

        final UserSimpleBiometricsDTO biometricsDTO = mapUserBiometrics(userBiometrics);
        return new ExerciseSearchResponseDTO(biometricsDTO, exercises);
    }

    public void editExerciseEntry(final List<ExerciseEntry> exerciseEntries,
                                  final EditedExerciseEntryDTO editedEntry) {
        final ExerciseEntry exerciseEntry = exerciseEntries
                .stream()
                .filter(entry -> entry.getPosition().equals(editedEntry.getPosition()))
                .findFirst()
                .orElseThrow(EntryNotFoundException::new);
        exerciseEntry.setAmount(editedEntry.getDuration());
        exerciseEntry.setTimeUnit(editedEntry.getUnit());

        exerciseRepository.save(exerciseEntry);
    }

    private static UserSimpleBiometricsDTO mapUserBiometrics(final UserBiometrics userBiometrics) {
        final Integer age = DateUtil.getUserAge(userBiometrics.getDateOfBirth());
        return new UserSimpleBiometricsDTO(
                age.shortValue(),
                userBiometrics.getSex(),
                userBiometrics.getHeight(),
                userBiometrics.getWeight());
    }

}
