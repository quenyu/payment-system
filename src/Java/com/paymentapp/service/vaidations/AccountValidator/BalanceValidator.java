package Java.com.paymentapp.service.vaidations.AccountValidator;

import Java.com.paymentapp.exception.validation.Error;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.service.vaidations.Validator;

import java.math.BigDecimal;

public class BalanceValidator implements Validator<
        BigDecimal> {

    @Override
    public ValidationResult validate(BigDecimal balance) {
        ValidationResult result = new ValidationResult();

        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            result.addError(Error.of("balance", "Balance cannot be negative"));
        }

        return result;
    }
}
