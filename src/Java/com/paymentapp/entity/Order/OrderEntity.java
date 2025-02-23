package Java.com.paymentapp.entity.Order;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderEntity {
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    private Integer userId;
    private Timestamp orderDate;
    private BigDecimal amount;
    private OrderStatus status;
}