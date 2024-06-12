package eu.garudaacademy.api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public void initialize(Password constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSpecialCharacter = false;

        for (Character character : value.toCharArray()) {
            if (Character.isLetter(character)) {
                hasLetter = true;
            } else if (Character.isDigit(character)) {
                hasDigit = true;
            } else if (isSpecialCharacter(character)) {
                hasSpecialCharacter = true;
            }
        }

        return hasLetter && hasDigit && hasSpecialCharacter;
    }

    private boolean isSpecialCharacter(Character character) {
        return character.toString().matches("[^A-Za-z0-9 ]");
    }
}
