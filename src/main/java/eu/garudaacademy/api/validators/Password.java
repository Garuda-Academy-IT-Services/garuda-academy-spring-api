package eu.garudaacademy.api.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD,  ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Nem megfelelő jelszó! A jelszónak tartalmaznia kell legalább egy betűt, egy számot és egy speciális karaktert is!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
