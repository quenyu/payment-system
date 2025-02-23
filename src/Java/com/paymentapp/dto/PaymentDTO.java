package Java.com.paymentapp.dto;

import Java.com.paymentapp.entity.Payment.PaymentStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Value
@Builder
public class PaymentDTO {
    Integer id;
    Integer fromAccountId;
    Integer toAccountId;
    Integer orderId;
    BigDecimal amount;
    Timestamp paymentDate;
    PaymentStatus status;
}