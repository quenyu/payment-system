package Java.com.paymentapp.service.vaidations.AccountValidator;

import Java.com.paymentapp.dao.UserDAO;
import Java.com.paymentapp.dto.AccountDTO;
import Java.com.paymentapp.exception.validation.Error;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.service.vaidations.Validator;

public class AccountValidator implements Validator<AccountDTO> {
    private final UserDAO userDAO = UserDAO.getInstance();
    private final NumberValidator numberValidator = new NumberValidator();
    private final BalanceValidator balanceValidator = new BalanceValidator();
    private final StatusValidator statusValidator = new StatusValidator();

    @Override
    public ValidationResult validate(AccountDTO accountDTO) {
        ValidationResult result = new ValidationResult();

        result.merge(numberValidator.validate(accountDTO.getAccountNumber()));

        validateUserExistence(accountDTO.getUserId(), result);

        result.merge(balanceValidator.validate(accountDTO.getBalance()));

        result.merge(statusValidator.validate(String.valueOf(accountDTO.getStatus())));

        return result;
    }

    private void validateUserExistence(int userId, ValidationResult result) {
        if (!userDAO.existsById(userId)) {
            result.addError(Error.of("user_id", "User does not exist"));
        }
    }
}