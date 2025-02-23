package Java.com.paymentapp.service.vaidations.UserValidator;

import Java.com.paymentapp.exception.validation.Error;
import Java.com.paymentapp.exception.validation.ValidationResult;

import java.util.Set;

public class RoleValidator {
    private static final Set<String> ALLOWED_ROLES = Set.of("CLIENT", "ADMIN");

    public ValidationResult validateRole(String role) {
        ValidationResult result = new ValidationResult();

        if (!ALLOWED_ROLES.contains(role)) {
            result.addError(Error.of("role", "Invalid user role"));
        }

        return result;
    }
}