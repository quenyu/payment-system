package Java.com.paymentapp.entity.CreditCard;

import Java.com.paymentapp.util.EnumUtil;

import java.util.Optional;

public enum CreditCardStatus {
    ACTIVE,
    BLOCKED;

    public static Optional<CreditCardStatus> find(String role) {
        return EnumUtil.find(CreditCardStatus.class, role);
    }
}
