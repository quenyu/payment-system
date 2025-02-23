package Java.com.paymentapp.service;

import Java.com.paymentapp.entity.Payment.PaymentStatus;
import Java.com.paymentapp.exception.validation.Error;
import Java.com.paymentapp.dto.PaymentDTO;
import Java.com.paymentapp.entity.Account.AccountEntity;
import Java.com.paymentapp.exception.validation.ValidationException;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.service.vaidations.TransferValidator.TransferRequest;
import Java.com.paymentapp.service.vaidations.TransferValidator.TransferValidator;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class TransferFundsService {
    private final AccountService accountService;
    private final TransferValidator transferValidator;

    public PaymentDTO transferFunds(int fromAccountId,
                                    String toAccountNumber,
                                    BigDecimal amount,
                                    int userId) {
        AccountEntity fromAccount = accountService.findById(fromAccountId).orElseThrow(() -> new ValidationException(Error.of("Account not found", "ACCOUNT_NOT_FOUND")));
        AccountEntity toAccount = accountService.findByAccountNumber(toAccountNumber).orElseThrow(() -> new ValidationException(Error.of("Account not found", "ACCOUNT_NOT_FOUND")));

        TransferRequest request = new TransferRequest(
                fromAccountId,
                fromAccount,
                toAccountNumber,
                toAccount,
                amount,
                userId
        );

        ValidationResult result = transferValidator.validate(request);
        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }

        AccountEntity updatedFrom = fromAccount.withBalance(fromAccount.getBalance().subtract(amount));
        AccountEntity updatedTo = toAccount.withBalance(toAccount.getBalance().add(amount));

        accountService.transferFundsTransactionally(updatedFrom, updatedTo);

        return PaymentDTO.builder()
                .fromAccountId(fromAccountId)
                .toAccountId(toAccount.getId())
                .amount(amount)
                .status(PaymentStatus.COMPLETED)
                .build();
    }
}