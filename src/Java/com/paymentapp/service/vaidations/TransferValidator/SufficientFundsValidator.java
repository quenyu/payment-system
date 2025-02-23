package Java.com.paymentapp.service.vaidations.TransferValidator;

import Java.com.paymentapp.exception.validation.Error;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.service.vaidations.Validator;

import java.math.BigDecimal;

public class SufficientFundsValidator implements Validator<TransferRequest> {
    @Override
    public ValidationResult validate(TransferRequest request) {
        ValidationResult result = new ValidationResult();
        BigDecimal balance = request.fromAccount().getBalance();

        if (balance.compareTo(request.amount()) < 0) {
            result.addError(Error.of("balance", "Недостаточно средств"));
        }

        return result;
    }
}