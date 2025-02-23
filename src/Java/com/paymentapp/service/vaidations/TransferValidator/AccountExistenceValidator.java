package Java.com.paymentapp.service.vaidations.TransferValidator;

import Java.com.paymentapp.dao.AccountDAO;
import Java.com.paymentapp.entity.Account.AccountEntity;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.service.AccountService;
import Java.com.paymentapp.service.vaidations.Validator;
import Java.com.paymentapp.exception.validation.Error;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountExistenceValidator implements Validator<TransferRequest> {
    private final AccountDAO accountDAO;

    @Override
    public ValidationResult validate(TransferRequest request) {
        ValidationResult result = new ValidationResult();

        boolean fromExists = accountDAO.existsById(request.fromAccountId());
        boolean toExists = accountDAO.existsByAccountNumber(request.toAccountNumber());

        if (!fromExists) result.addError(Error.of("fromAccount", "Not found"));
        if (!toExists) result.addError(Error.of("toAccount", "Not found"));

        return result;
    }
}