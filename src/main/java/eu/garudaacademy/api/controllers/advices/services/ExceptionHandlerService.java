package eu.garudaacademy.api.controllers.advices.services;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public class ExceptionHandlerService {

    public String getFieldAndErrorFromValidatorExceptions(final MethodArgumentNotValidException e) {
        if (e.getBindingResult().getFieldError() != null) {
            return "Hiba a '" + e.getBindingResult().getFieldError().getField()
                    + "' mezőn: " + e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        } else {
            return "ISMERETLEN HIBA! " + e.getBindingResult().getAllErrors().get(0).toString();
        }
    }

    public String getCleanedExceptionName(final Exception e) {
        return e.getClass().getSimpleName().replaceAll("Exception", "");
    }
}
