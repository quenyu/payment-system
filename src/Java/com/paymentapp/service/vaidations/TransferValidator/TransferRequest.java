package Java.com.paymentapp.service.vaidations.TransferValidator;

import Java.com.paymentapp.entity.Account.AccountEntity;

import java.math.BigDecimal;

public record TransferRequest(
        int fromAccountId,
        AccountEntity fromAccount,
        String toAccountNumber,
        AccountEntity toAccount,
        BigDecimal amount,
        int userId
) {}