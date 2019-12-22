package pl.polsl.wachowski.nutritionassistant.validation.annotation;

import pl.polsl.wachowski.nutritionassistant.validation.PasswordMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
public @interface PasswordMatch {
    String message() default "Password confirmation does not match password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
