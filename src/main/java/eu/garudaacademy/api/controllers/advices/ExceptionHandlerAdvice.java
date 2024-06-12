package eu.garudaacademy.api.controllers.advices;

import eu.garudaacademy.api.controllers.advices.services.ExceptionHandlerService;
import eu.garudaacademy.api.models.responses.ErrorResponse;
import io.jsonwebtoken.MalformedJwtException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @Autowired
    private ExceptionHandlerService exceptionHandlerService;

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJwtException(Exception e, WebRequest request) {
        int badRequestCode = HttpStatus.BAD_REQUEST.value();

        return ResponseEntity.status(badRequestCode)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .status(badRequestCode)
                        .errorName(exceptionHandlerService.getCleanedExceptionName(e))
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        int badRequestCode = HttpStatus.BAD_REQUEST.value();

        return ResponseEntity.status(badRequestCode)
                .body(ErrorResponse.builder()
                        .message(exceptionHandlerService.getFieldAndErrorFromValidatorExceptions(e))
                        .status(badRequestCode)
                        .errorName(exceptionHandlerService.getCleanedExceptionName(e))
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, WebRequest request) {
        int internalServerErrorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

        return ResponseEntity.status(internalServerErrorCode)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .status(internalServerErrorCode)
                        .errorName(exceptionHandlerService.getCleanedExceptionName(e))
                        .build());
    }
}
