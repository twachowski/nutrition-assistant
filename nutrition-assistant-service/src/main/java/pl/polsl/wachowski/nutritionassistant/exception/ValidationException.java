package pl.polsl.wachowski.nutritionassistant.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.ConstraintViolation;
import java.util.Set;

@AllArgsConstructor
@Getter
public class ValidationException extends RuntimeException {

    private final Set<ConstraintViolation<Object>> violations;

}
