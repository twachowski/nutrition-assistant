package pl.polsl.wachowski.nutritionassistant.util;

import pl.polsl.wachowski.nutritionassistant.exception.validation.ValidationException;

public final class ViolationUtils {

    private ViolationUtils() {
    }

    public static <T> boolean hasViolation(final ValidationException exception,
                                           final Class<T> rootBeanClass,
                                           final String propertyPath) {
        return exception.getViolations()
                .stream()
                .anyMatch(violation -> violation.getRootBeanClass().equals(rootBeanClass)
                        && violation.getPropertyPath().toString().equals(propertyPath));
    }

}
