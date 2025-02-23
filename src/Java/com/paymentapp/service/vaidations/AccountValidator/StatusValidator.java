package Java.com.paymentapp.service.vaidations.AccountValidator;

import Java.com.paymentapp.entity.Account.AccountStatus;
import Java.com.paymentapp.exception.validation.Error;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.service.vaidations.Validator;

import java.util.Set;

public class StatusValidator implements Validator<String> {
    private static final Set<String> ALLOWED_STATUSES = Set.of("ACTIVE", "CANCELLED");

    @Override
    public ValidationResult validate(String role) {
        ValidationResult result = new ValidationResult();

        if (!ALLOWED_STATUSES.contains(role)) {
            result.addError(Error.of("status", "Invalid user role"));
        }

        return result;
    }
}
