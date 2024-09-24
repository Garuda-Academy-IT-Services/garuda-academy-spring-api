package eu.garudaacademy.api.controllers.advices;

import eu.garudaacademy.api.controllers.advices.services.ExceptionHandlerService;
import eu.garudaacademy.api.models.responses.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ValidationException;

@RestControllerAdvice
@Order(1)
public class UserCreationExceptionHandlerAdvice {

    private static final String UK_EMAIL_CONSTRAINT = "[uk_email]";
    private static final String UK_EMAIL_ERROR_CODE = "EmailAlreadyRegistered";
    private static final String UK_EMAIL_ERROR_MSG = "Ez az email cím már használatban van!";
    private static final String UK_USERNAME_CONSTRAINT = "uk_r43af9ap4edm43mmtq01oddj6";
    private static final String UK_USERNAME_ERROR_CODE = "UsernameAlreadyRegistered";
    private static final String UK_USERNAME_ERROR_MSG = "Ez a felhaszálónév már használatban van.";

    @Autowired
    private ExceptionHandlerService exceptionHandlerService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        int badRequestCode = HttpStatus.BAD_REQUEST.value();

        return ResponseEntity.status(badRequestCode)
                .body(ErrorResponse.builder()
                        .message(exceptionHandlerService.getFieldAndErrorFromValidatorExceptions(e))
                        .status(badRequestCode)
                        .errorName(exceptionHandlerService.getErrorCodeString(e))
                        .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(DataIntegrityViolationException e, WebRequest request) {
        int badRequestCode = HttpStatus.BAD_REQUEST.value();

        if (e.getMessage() != null && e.getMessage().contains(UK_EMAIL_CONSTRAINT)) {
            return ResponseEntity.status(badRequestCode)
                    .body(ErrorResponse.builder()
                            .message(UK_EMAIL_ERROR_MSG)
                            .status(badRequestCode)
                            .errorName(UK_EMAIL_ERROR_CODE)
                            .build());

        } else if (e.getMessage() != null && e.getMessage().contains(UK_USERNAME_CONSTRAINT)) {
            return ResponseEntity.status(badRequestCode)
                    .body(ErrorResponse.builder()
                            .message(UK_USERNAME_ERROR_MSG)
                            .status(badRequestCode)
                            .errorName(UK_USERNAME_ERROR_CODE)
                            .build());

        } else {
            return ResponseEntity.status(badRequestCode)
                    .body(ErrorResponse.builder()
                            .message(e.getMessage())
                            .status(badRequestCode)
                            .errorName(exceptionHandlerService.getCleanedExceptionName(e))
                            .build());
        }
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException e, WebRequest request) {
        int badRequestCode = HttpStatus.BAD_REQUEST.value();

        return ResponseEntity.status(badRequestCode)
                .body(ErrorResponse.builder()
                        .message("A küldött json nem valid. A username, password és email mezők megadása kötelező.")
                        .status(badRequestCode)
                        .errorName("JsonValidationError")
                        .build());
    }
}
