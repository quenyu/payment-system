package Java.com.paymentapp.dto;

import Java.com.paymentapp.entity.CreditCard.CreditCardStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreditCardReadDTO {
    Integer id;
    String last4Digits;
    CreditCardStatus status;
    String email;
}
