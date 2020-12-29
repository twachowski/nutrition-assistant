package pl.polsl.wachowski.nutritionassistant.api.targets;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class HighlightedTargetsRequest {

    @NotBlank
    String target1;

    @NotBlank
    String target2;

    @NotBlank
    String target3;

    @NotBlank
    String target4;

    @NotBlank
    String target5;

    @NotBlank
    String target6;

}
