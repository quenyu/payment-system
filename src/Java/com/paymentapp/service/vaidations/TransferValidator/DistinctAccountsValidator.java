package Java.com.paymentapp.service.vaidations.TransferValidator;

import Java.com.paymentapp.exception.validation.Error;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.service.vaidations.Validator;

public class DistinctAccountsValidator implements Validator<TransferRequest> {
    @Override
    public ValidationResult validate(TransferRequest request) {
        ValidationResult result = new ValidationResult();

        if (request.fromAccountId() == request.toAccount().getId()) {
            result.addError(Error.of("accounts", "Нельзя перевести на тот же счет"));
        }

        return result;
    }
}