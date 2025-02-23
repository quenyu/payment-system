package Java.com.paymentapp.entity.CreditCard;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CreditCardEntity {
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    private Integer accountId;
    private String cardNumber;
    private BigDecimal creditLimit;
    private BigDecimal currentUsage;
    private CreditCardStatus status;
    private Timestamp createdAt;
}