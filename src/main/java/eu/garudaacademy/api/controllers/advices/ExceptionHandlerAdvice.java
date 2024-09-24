package eu.garudaacademy.api.controllers.advices;

import eu.garudaacademy.api.controllers.advices.services.ExceptionHandlerService;
import eu.garudaacademy.api.models.exception.ResourceNotFoundException;
import eu.garudaacademy.api.models.responses.ErrorResponse;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Order(2)
public class ExceptionHandlerAdvice {

    @Autowired
    private ExceptionHandlerService exceptionHandlerService;

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJwtException(MalformedJwtException e, WebRequest request) {
        int badRequestCode = HttpStatus.FORBIDDEN.value();

        return ResponseEntity.status(badRequestCode)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .status(badRequestCode)
                        .errorName(exceptionHandlerService.getCleanedExceptionName(e))
                        .build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e, WebRequest request) {
        int badRequestCode = HttpStatus.FORBIDDEN.value();

        return ResponseEntity.status(badRequestCode)
                .body(ErrorResponse.builder()
                        .message("Hibas felhasznalonev vagy jelszo!")
                        .status(badRequestCode)
                        .errorName(exceptionHandlerService.getCleanedExceptionName(e))
                        .build());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e, WebRequest request) {
        int badRequestCode = HttpStatus.FORBIDDEN.value();

        return ResponseEntity.status(badRequestCode)
                .body(ErrorResponse.builder()
                        .message("Hibas felhasznalonev vagy jelszo!")
                        .status(badRequestCode)
                        .errorName(exceptionHandlerService.getCleanedExceptionName(e))
                        .build());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e, WebRequest request) {
        int badRequestCode = HttpStatus.FORBIDDEN.value();

        return ResponseEntity.status(badRequestCode)
                .body(ErrorResponse.builder()
                        .message("Hibas felhasznalonev vagy jelszo!")
                        .status(badRequestCode)
                        .errorName(exceptionHandlerService.getCleanedExceptionName(e))
                        .build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        int badRequestCode = HttpStatus.NOT_FOUND.value();

        return ResponseEntity.status(badRequestCode)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
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
