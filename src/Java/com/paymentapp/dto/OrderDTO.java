package Java.com.paymentapp.dto;

import Java.com.paymentapp.entity.Order.OrderStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Value
@Builder
public class OrderDTO {
    Integer id;
    Integer userId;
    Timestamp orderDate;
    BigDecimal amount;
    OrderStatus status;
}