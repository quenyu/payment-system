package Java.com.paymentapp.dto;

import Java.com.paymentapp.entity.Account.AccountStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Value
@Builder
public class AccountDTO {
    Integer id;
    Integer userId;
    String accountNumber;
    BigDecimal balance;
    AccountStatus status;
    Timestamp createdAt;
    String currency;
}