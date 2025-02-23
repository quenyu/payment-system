package Java.com.paymentapp.dto;

import Java.com.paymentapp.entity.CreditCard.CreditCardStatus;
import lombok.Builder;
import lombok.Value;

import java.sql.Timestamp;

@Value
@Builder
public class AdminCreditCardDTO {
    Integer id;
    String last4Digits;
    CreditCardStatus status;
    String userEmail;
    String accountNumber;
    Timestamp createdAt;
}