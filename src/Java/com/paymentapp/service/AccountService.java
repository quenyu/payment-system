package Java.com.paymentapp.service;

import Java.com.paymentapp.dao.AccountDAO;
import Java.com.paymentapp.dao.UserDAO;
import Java.com.paymentapp.dto.AccountDTO;
import Java.com.paymentapp.dto.PaymentDTO;
import Java.com.paymentapp.entity.Account.AccountEntity;
import Java.com.paymentapp.entity.Account.AccountStatus;
import Java.com.paymentapp.entity.Payment.PaymentStatus;
import Java.com.paymentapp.exception.validation.Error;
import Java.com.paymentapp.exception.validation.ValidationException;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.mapper.AccountMapper;
import Java.com.paymentapp.service.vaidations.AccountValidator.AccountValidator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class AccountService {
    private final UserDAO userDAO;
    private final AccountDAO accountDAO;
    private final AccountMapper accountMapper;
    private final AccountValidator accountValidator;

    @SneakyThrows
    public BigDecimal calculateTotalBalance(int userId) {
        return accountDAO.getTotalBalanceByUserId(userId);
    }

    public List<AccountEntity> getAllAccounts() {
        return accountDAO.findAll();
    }

    public List<AccountEntity> getUserAccounts(int userId) {
        return accountDAO.findByUserId(userId);
    }

    public Optional<AccountEntity> findById(int accountId) {
        return accountDAO.findById(accountId);
    }

    public Optional<AccountEntity> findByAccountNumber(String accountNumber) {
        return accountDAO.findByAccountNumber(accountNumber);
    }

    public void transferFundsTransactionally(AccountEntity updatedFrom, AccountEntity updatedTo) {
        accountDAO.transferFundsTransactionally(updatedFrom, updatedTo);
    }

    @SneakyThrows
    public void closeAccount(int accountId, int userId) {
        AccountEntity account = accountDAO.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + accountId));

        if (account.getUserId() != userId) {
            throw new SecurityException("Access denied");
        }

        accountDAO.deleteById(accountId);
    }

    public void createAccount(Integer userId, String currency) {
        String accountNumber = generateAccountNumber(currency);

        AccountDTO accountDTO = AccountDTO.builder()
                .userId(userId)
                .accountNumber(accountNumber)
                .balance(BigDecimal.ZERO)
                .status(AccountStatus.ACTIVE)
                .currency(currency)
                .build();

        ValidationResult validationResult = accountValidator.validate(accountDTO);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        AccountEntity account = accountMapper.toEntity(accountDTO);
        accountDAO.save(account);
    }

    public void updateAccount(AccountDTO accountDTO) {
        ValidationResult result = accountValidator.validate(accountDTO);
        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }
        accountDAO.update(accountMapper.toEntity(accountDTO));
    }

    @SneakyThrows
    public PaymentDTO transferFunds(int fromAccountId, String toAccountNumber, BigDecimal amount, int userId) {
        AccountEntity fromAccount = accountDAO.findById(fromAccountId)
                .orElseThrow(AccountNotFoundException::new);

        if (fromAccount.getUserId() != userId) {
            throw new ValidationException(Error.of("Account ownership validation failed", "ACCOUNT_OWNERSHIP_ERROR"));
        }

        AccountEntity toAccount = accountDAO.findByAccountNumber(toAccountNumber)
                .orElseThrow(AccountNotFoundException::new);

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new ValidationException(Error.of("Insufficient funds", "INSUFFICIENT_FUNDS"));
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountDAO.update(fromAccount);
        accountDAO.update(toAccount);

        return PaymentDTO.builder()
                .fromAccountId(fromAccountId)
                .toAccountId(toAccount.getId())
                .amount(amount)
                .status(PaymentStatus.COMPLETED)
                .build();
    }

    private String generateAccountNumber(String currency) {
        String prefix = "RU";
        if ("USD".equals(currency)) {
            prefix = "US";
        }
        return prefix + ThreadLocalRandom.current()
                .nextLong(100000000000000000L, 999999999999999999L);
    }
}
