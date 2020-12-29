package pl.polsl.wachowski.nutritionassistant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.wachowski.nutritionassistant.api.nutrients.Nutrient;
import pl.polsl.wachowski.nutritionassistant.api.targets.HighlightedTargetsRequest;
import pl.polsl.wachowski.nutritionassistant.api.targets.HighlightedTargetsResponse;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.targets.HighlightedTargetsEntity;
import pl.polsl.wachowski.nutritionassistant.domain.repository.HighlightedTargetsRepository;
import pl.polsl.wachowski.nutritionassistant.exception.target.UnknownTargetNutrientException;
import pl.polsl.wachowski.nutritionassistant.util.NutrientHelper;

import java.util.Optional;

@Service
public class HighlightedTargetsService {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final HighlightedTargetsRepository highlightedTargetsRepository;

    @Autowired
    public HighlightedTargetsService(final AuthenticationService authenticationService,
                                     final UserService userService,
                                     final HighlightedTargetsRepository highlightedTargetsRepository) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.highlightedTargetsRepository = highlightedTargetsRepository;
    }

    public HighlightedTargetsResponse getAuthenticatedUserHighlightedTargets() {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        final UserEntity user = Optional.ofNullable(userService.getUserByEmail(userEmail))
                .orElseThrow(IllegalStateException::new);
        final HighlightedTargetsEntity targets = getUserHighlightedTargets(user);
        return toHighlightedTargetsResponse(targets);
    }

    public void updateUserHighlightedTargets(final HighlightedTargetsRequest request) throws UnknownTargetNutrientException {
        final String userEmail = authenticationService.getAuthenticatedUserEmail();
        final UserEntity user = Optional.ofNullable(userService.getUserByEmail(userEmail))
                .orElseThrow(IllegalStateException::new);
        final HighlightedTargetsEntity targets = getUserHighlightedTargets(user);
        targets.setNutrient1(parseNutrient(request.getTarget1()));
        targets.setNutrient2(parseNutrient(request.getTarget2()));
        targets.setNutrient3(parseNutrient(request.getTarget3()));
        targets.setNutrient4(parseNutrient(request.getTarget4()));
        targets.setNutrient5(parseNutrient(request.getTarget5()));
        targets.setNutrient6(parseNutrient(request.getTarget6()));

        highlightedTargetsRepository.save(targets);
    }

    private HighlightedTargetsEntity getUserHighlightedTargets(final UserEntity user) {
        return highlightedTargetsRepository.findHighlightedTargetsByUserEmail(user.getEmail())
                .orElseGet(() -> HighlightedTargetsEntity.getDefault(user));
    }

    private static HighlightedTargetsResponse toHighlightedTargetsResponse(final HighlightedTargetsEntity targets) {
        return new HighlightedTargetsResponse(toNutrient(targets.getNutrient1()),
                                              toNutrient(targets.getNutrient2()),
                                              toNutrient(targets.getNutrient3()),
                                              toNutrient(targets.getNutrient4()),
                                              toNutrient(targets.getNutrient5()),
                                              toNutrient(targets.getNutrient6()));
    }

    private static Nutrient toNutrient(final String nutrientName) {
        return NutrientHelper.fromName(nutrientName)
                .orElseThrow(() -> new IllegalStateException("Unknown nutrient name: " + nutrientName));
    }

    private static Nutrient parseNutrient(final String nutrientName) throws UnknownTargetNutrientException {
        return NutrientHelper.fromName(nutrientName)
                .orElseThrow(() -> new UnknownTargetNutrientException(nutrientName));
    }

}
