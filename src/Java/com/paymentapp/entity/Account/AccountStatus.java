package Java.com.paymentapp.entity.Account;

import Java.com.paymentapp.util.EnumUtil;

import java.util.Arrays;
import java.util.Optional;

public enum AccountStatus {
    ACTIVE,
    BLOCKED,
    CANCELLED;

    public static Optional<AccountStatus> find(String role) {
        return EnumUtil.find(AccountStatus.class, role);
    }
}
