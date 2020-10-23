package pl.polsl.wachowski.nutritionassistant.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.wachowski.nutritionassistant.api.error.ErrorResponse;
import pl.polsl.wachowski.nutritionassistant.exception.InvalidCredentialsException;
import pl.polsl.wachowski.nutritionassistant.exception.UserAlreadyActiveException;
import pl.polsl.wachowski.nutritionassistant.exception.UserExistsException;
import pl.polsl.wachowski.nutritionassistant.exception.UserInactiveException;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenExpiredException;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenNotFoundException;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionControllerAdvice {

    @ExceptionHandler({
            UserExistsException.class,
            VerificationTokenExpiredException.class,
            VerificationTokenNotFoundException.class,
            UserAlreadyActiveException.class,
            InvalidCredentialsException.class,
            UserInactiveException.class
    })
    public ResponseEntity<ErrorResponse> handleGeneralException(final Exception exception) {
        final ErrorResponse error = new ErrorResponse(exception.getMessage());
        return badRequest(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(final MethodArgumentNotValidException exception) {
        final BindingResult bindingResult = exception.getBindingResult();
        final ObjectError error = bindingResult.getFieldErrorCount() > 0
                ? bindingResult.getFieldError()
                : bindingResult.getGlobalError();

        final ErrorResponse errorDTO = new ErrorResponse(error.getDefaultMessage());
        return badRequest(errorDTO);
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ErrorResponse> handleMailErrors(final MailException exception) {
        final ErrorResponse error = new ErrorResponse("An error occurred while sending an email with confirmation link");
        return internalError(error);
    }

    private static ResponseEntity<ErrorResponse> badRequest(final ErrorResponse error) {
        return ResponseEntity
                .badRequest()
                .body(error);
    }

    private static ResponseEntity<ErrorResponse> internalError(final ErrorResponse error) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

}
