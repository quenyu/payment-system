package Java.com.paymentapp.entity.Order;

import Java.com.paymentapp.util.EnumUtil;

import java.util.Optional;

public enum OrderStatus {
    PENDING,
    PAID,
    CANCELLED;

    public static Optional<OrderStatus> find(String role) {
        return EnumUtil.find(OrderStatus.class, role);
    }
}
