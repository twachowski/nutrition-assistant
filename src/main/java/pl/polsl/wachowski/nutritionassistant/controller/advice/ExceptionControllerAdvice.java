package pl.polsl.wachowski.nutritionassistant.controller.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationErrors(final MethodArgumentNotValidException ex) {
        final BindingResult bindingResult = ex.getBindingResult();
        final ObjectError error = bindingResult.getFieldErrorCount() > 0
                ? bindingResult.getFieldError()
                : bindingResult.getGlobalError();

        return ResponseEntity
                .badRequest()
                .body(error.getDefaultMessage());
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity handleMailErrors(final MailException ex) {
        return ResponseEntity
                .badRequest()
                .body("An error occurred while sending an email with confirmation link");
    }

}
