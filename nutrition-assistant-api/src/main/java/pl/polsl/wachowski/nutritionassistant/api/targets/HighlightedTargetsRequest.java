package pl.polsl.wachowski.nutritionassistant.api.targets;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class HighlightedTargetsRequest {

    @NotBlank(message = "Target 1 must not be blank")
    String target1;

    @NotBlank(message = "Target 2 must not be blank")
    String target2;

    @NotBlank(message = "Target 3 must not be blank")
    String target3;

    @NotBlank(message = "Target 4 must not be blank")
    String target4;

    @NotBlank(message = "Target 5 must not be blank")
    String target5;

    @NotBlank(message = "Target 6 must not be blank")
    String target6;

}
