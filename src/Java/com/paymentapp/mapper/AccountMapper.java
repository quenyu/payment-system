package Java.com.paymentapp.mapper;

import Java.com.paymentapp.dto.AccountDTO;
import Java.com.paymentapp.entity.Account.AccountEntity;

public class AccountMapper implements Mapper<AccountDTO, AccountEntity> {
    private static final AccountMapper INSTANCE = new AccountMapper();

    @Override
    public AccountEntity toEntity(AccountDTO account) {
        return new AccountEntity(
                account.getId(),
                account.getUserId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getStatus(),
                account.getCreatedAt(),
                account.getCurrency()
        );
    }

    public static AccountMapper getInstance() {
        return INSTANCE;
    }
}
