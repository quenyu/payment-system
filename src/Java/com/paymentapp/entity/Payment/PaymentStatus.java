package Java.com.paymentapp.entity.Payment;

import Java.com.paymentapp.util.EnumUtil;

import java.util.Optional;

public enum PaymentStatus {
    COMPLETED,
    PENDING,
    FAILED;

    public static Optional<PaymentStatus> find(String role) {
        return EnumUtil.find(PaymentStatus.class, role);
    }
}