package Java.com.paymentapp.entity.Payment;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PaymentEntity {
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    private Integer fromAccountId;
    private Integer toAccountId;
    private Integer orderId;
    private BigDecimal amount;
    private Timestamp paymentDate;
    private PaymentStatus status;
}