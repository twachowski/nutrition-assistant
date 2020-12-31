package pl.polsl.wachowski.nutritionassistant.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import pl.polsl.wachowski.nutritionassistant.api.error.Error;
import pl.polsl.wachowski.nutritionassistant.api.error.ErrorResponse;
import pl.polsl.wachowski.nutritionassistant.exception.entry.EntryNotFoundException;
import pl.polsl.wachowski.nutritionassistant.exception.provider.FdcException;
import pl.polsl.wachowski.nutritionassistant.exception.provider.NutritionixException;
import pl.polsl.wachowski.nutritionassistant.exception.target.UnknownTargetNutrientException;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenException;
import pl.polsl.wachowski.nutritionassistant.exception.token.VerificationTokenExpiredException;
import pl.polsl.wachowski.nutritionassistant.exception.user.InvalidCredentialsException;
import pl.polsl.wachowski.nutritionassistant.exception.user.UserAlreadyActiveException;
import pl.polsl.wachowski.nutritionassistant.exception.user.UserExistsException;
import pl.polsl.wachowski.nutritionassistant.exception.user.UserInactiveException;
import pl.polsl.wachowski.nutritionassistant.exception.validation.ValidationException;

import javax.validation.ConstraintViolation;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class ControllerExceptionHandler {

    @ExceptionHandler(EntryNotFoundException.class)
    ResponseEntity<ErrorResponse> handleEntryNotFoundException(final EntryNotFoundException e) {
        final ErrorResponse errorResponse = new ErrorResponse(Error.ENTRY_NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(UnknownTargetNutrientException.class)
    ResponseEntity<ErrorResponse> handleUnknownTargetNutrientException(final UnknownTargetNutrientException e) {
        final ErrorResponse errorResponse = new ErrorResponse(Error.UNKNOWN_NUTRIENT, e.getMessage());
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(UserExistsException.class)
    ResponseEntity<ErrorResponse> handleUserExistsException(final UserExistsException e) {
        final ErrorResponse errorResponse = new ErrorResponse(Error.USER_ALREADY_EXISTS, e.getMessage());
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyActiveException.class)
    ResponseEntity<ErrorResponse> handleUserAlreadyActiveException(final UserAlreadyActiveException e) {
        final ErrorResponse errorResponse = new ErrorResponse(Error.USER_ALREADY_ACTIVE, e.getMessage());
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(UserInactiveException.class)
    ResponseEntity<ErrorResponse> handleUserInactiveException(final UserInactiveException e) {
        final ErrorResponse errorResponse = new ErrorResponse(Error.USER_INACTIVE, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(errorResponse);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    ResponseEntity<ErrorResponse> handleInvalidCredentialsException(final InvalidCredentialsException e) {
        final ErrorResponse errorResponse = new ErrorResponse(Error.INVALID_CREDENTIALS, e.getMessage());
        return ResponseEntity.badRequest()  //TODO change to 401 when basic auth is used
                .body(errorResponse);
    }

    @ExceptionHandler(VerificationTokenException.class)
    ResponseEntity<ErrorResponse> handleVerificationTokenException(final VerificationTokenException e) {
        final Error error = e instanceof VerificationTokenExpiredException ? Error.TOKEN_EXPIRED : Error.TOKEN_NOT_FOUND;
        final ErrorResponse errorResponse = new ErrorResponse(error, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    @ExceptionHandler(FdcException.class)
    ResponseEntity<ErrorResponse> handleFdcException(final FdcException e) {
        final ErrorResponse errorResponse = new ErrorResponse(Error.REMOTE_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler(NutritionixException.class)
    ResponseEntity<ErrorResponse> handleNutritionixException(final NutritionixException e) {
        final ErrorResponse errorResponse = new ErrorResponse(Error.REMOTE_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<ErrorResponse> handleValidationException(final ValidationException e) {
        log.error("Validation exception has occurred", e);
        final String errors = e.getViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(" | "));
        final ErrorResponse errorResponse = new ErrorResponse(Error.REQUEST_VALIDATION_ERROR, errors);
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ErrorResponse> handleRuntimeException(final RuntimeException e) {
        log.error("An unexpected runtime error has occurred", e);
        final ErrorResponse errorResponse = new ErrorResponse(Error.OTHER_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        log.error("HTTP method '{}' is not supported", e.getMethod(), e);
        final ErrorResponse errorResponse = new ErrorResponse(Error.OTHER_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("Validation error has occurred", e);
        final String errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" | "));
        final ErrorResponse errorResponse = new ErrorResponse(Error.REQUEST_VALIDATION_ERROR, errors);
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(final HttpMediaTypeNotSupportedException e) {
        log.error("Content type '{}' is not supported", e.getContentType(), e);
        final ErrorResponse errorResponse = new ErrorResponse(Error.OTHER_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        log.error("Non-readable HTTP message has been detected", e);
        final ErrorResponse errorResponse = new ErrorResponse(Error.REQUEST_VALIDATION_ERROR, e.getMessage());
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        log.error("Inconvertible method argument has been detected", e);
        final ErrorResponse errorResponse = new ErrorResponse(Error.REQUEST_VALIDATION_ERROR, e.getMessage());
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

}
