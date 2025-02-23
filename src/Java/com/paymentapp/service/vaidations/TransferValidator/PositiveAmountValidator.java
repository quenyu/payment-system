package Java.com.paymentapp.service.vaidations.TransferValidator;

import Java.com.paymentapp.exception.validation.Error;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.service.vaidations.Validator;

import java.math.BigDecimal;

public class PositiveAmountValidator implements Validator<TransferRequest> {
    @Override
    public ValidationResult validate(TransferRequest request) {
        ValidationResult result = new ValidationResult();

        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            result.addError(Error.of("amount", "Сумма должна быть положительной"));
        }

        return result;
    }
}