package Java.com.paymentapp.service.vaidations.TransferValidator;

import Java.com.paymentapp.dao.AccountDAO;
import Java.com.paymentapp.entity.Account.AccountEntity;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.service.AccountService;
import Java.com.paymentapp.service.vaidations.Validator;

import java.math.BigDecimal;

public class TransferValidator implements Validator<TransferRequest> {
    private final AccountDAO accountDAO = AccountDAO.getInstance();
//    private final AccountExistenceValidator existenceValidator = new AccountExistenceValidator(accountDAO);
    private final AccountOwnershipValidator ownershipValidator = new AccountOwnershipValidator();
    private final SufficientFundsValidator fundsValidator = new SufficientFundsValidator();
    private final DistinctAccountsValidator distinctValidator = new DistinctAccountsValidator();
    private final PositiveAmountValidator amountValidator = new PositiveAmountValidator();


    @Override
    public ValidationResult validate(TransferRequest request) {
        ValidationResult result = new ValidationResult();

//        result.merge(existenceValidator.validate(request));
        result.merge(ownershipValidator.validate(request));
        result.merge(fundsValidator.validate(request));
        result.merge(distinctValidator.validate(request));
        result.merge(amountValidator.validate(request));

        return result;
    }
}