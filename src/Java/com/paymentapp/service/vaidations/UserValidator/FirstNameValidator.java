package Java.com.paymentapp.service.vaidations.UserValidator;

import Java.com.paymentapp.exception.validation.Error;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.service.vaidations.Validator;

public class FirstNameValidator implements Validator<String> {
    @Override
    public ValidationResult validate(String firstName) {
        ValidationResult result = new ValidationResult();

        if (firstName == null || firstName.trim().isEmpty()) {
            result.addError(Java.com.paymentapp.exception.validation.Error.of("firstName", "First name is required"));
        } else if (firstName.length() > 100) {
            result.addError(Error.of("firstName", "First name cannot exceed 100 characters"));
        }

        return result;
    }
}
