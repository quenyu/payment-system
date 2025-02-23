package Java.com.paymentapp.service.vaidations.UserValidator;

import Java.com.paymentapp.exception.validation.Error;
import Java.com.paymentapp.exception.validation.ValidationResult;

public class PasswordValidator {
    public ValidationResult validatePassword(String password) {
        ValidationResult result = new ValidationResult();

        if (password.length() < 8) {
            result.addError(Error.of("password", "Password must be at least 8 characters"));
        }

        if (!password.matches(".*[A-Z].*")) {
            result.addError(Error.of("password", "Password must contain at least one uppercase letter"));
        }

        if (!password.matches(".*[!@#$%^&*()_=+.,-].*")) {
            result.addError(Error.of("password", "Password must contain at least one special character"));
        }

        return result;
    }
}