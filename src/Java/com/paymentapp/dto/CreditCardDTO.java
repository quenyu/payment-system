package Java.com.paymentapp.dto;

import Java.com.paymentapp.entity.CreditCard.CreditCardStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Value
@Builder
public class CreditCardDTO {
    Integer id;
    Integer accountId;
    String cardNumber;
    BigDecimal creditLimit;
    BigDecimal currentUsage;
    CreditCardStatus status;
    Timestamp createdAt;
}