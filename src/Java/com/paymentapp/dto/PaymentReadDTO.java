package Java.com.paymentapp.dto;

import Java.com.paymentapp.entity.Payment.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentReadDTO {
    private Timestamp paymentDate;
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
    private String currency;
    private boolean outgoing;
    private PaymentStatus status;
}