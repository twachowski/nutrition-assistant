package pl.polsl.wachowski.nutritionassistant.samples;

import pl.polsl.wachowski.nutritionassistant.api.exercise.Exercise;
import pl.polsl.wachowski.nutritionassistant.api.exercise.ExerciseSearchResponse;
import pl.polsl.wachowski.nutritionassistant.api.user.Sex;
import pl.polsl.wachowski.nutritionassistant.api.user.UserSimpleBiometrics;

import java.math.BigDecimal;
import java.util.Collections;

public final class ExerciseSearchResponseSamples {

    private ExerciseSearchResponseSamples() {
    }

    public static ExerciseSearchResponse withRunningExercise() {
        final UserSimpleBiometrics biometrics = new UserSimpleBiometrics((short) 25,
                                                                         Sex.MALE,
                                                                         (short) 180,
                                                                         BigDecimal.valueOf(180));
        final Exercise exercise = new Exercise("running", BigDecimal.valueOf(280));
        return new ExerciseSearchResponse(biometrics, Collections.singleton(exercise));
    }

}
