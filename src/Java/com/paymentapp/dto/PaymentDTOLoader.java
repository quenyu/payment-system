package Java.com.paymentapp.dto;

import Java.com.paymentapp.entity.Payment.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentDTOLoader {
    LocalDateTime paymentDate;
    String fromAccountNumber;
    String toAccountNumber;
    BigDecimal amount;
    String currency;
    PaymentStatus status;
    boolean isOutgoing;
}