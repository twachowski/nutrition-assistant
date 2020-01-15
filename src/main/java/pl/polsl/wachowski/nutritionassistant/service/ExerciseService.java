package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.data.provider.exercise.ExerciseDataProviderAdapter;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.ExerciseDetailsDTO;
import pl.polsl.wachowski.nutritionassistant.dto.exercise.ExerciseSearchRequestDTO;

import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseDataProviderAdapter provider;

    @Autowired
    public ExerciseService(final ExerciseDataProviderAdapter provider) {
        this.provider = provider;
    }

    public List<ExerciseDetailsDTO> search(final ExerciseSearchRequestDTO request) {
        return provider.search(request);
    }

}
