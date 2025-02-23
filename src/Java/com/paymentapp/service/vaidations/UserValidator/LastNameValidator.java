package Java.com.paymentapp.service.vaidations.UserValidator;

import Java.com.paymentapp.exception.validation.Error;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.service.vaidations.Validator;

public class LastNameValidator implements Validator<String> {
    @Override
    public ValidationResult validate(String lastName) {
        ValidationResult result = new ValidationResult();

        if (lastName == null || lastName.trim().isEmpty()) {
            result.addError(Java.com.paymentapp.exception.validation.Error.of("lastName", "Last name is required"));
        } else if (lastName.length() > 100) {
            result.addError(Error.of("lastName", "Last name cannot exceed 100 characters"));
        }

        return result;
    }
}
