package Java.com.paymentapp.service.vaidations.TransferValidator;

import Java.com.paymentapp.entity.Account.AccountEntity;
import Java.com.paymentapp.exception.validation.Error;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.service.vaidations.Validator;

public class AccountOwnershipValidator implements Validator<TransferRequest> {
    @Override
    public ValidationResult validate(TransferRequest request) {
        ValidationResult result = new ValidationResult();
        AccountEntity account = request.fromAccount();

        if (account.getUserId() != request.userId()) {
            result.addError(Error.of("ownership", "Счет не принадлежит пользователю"));
        }

        return result;
    }
}