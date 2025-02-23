package Java.com.paymentapp.entity.User;

import Java.com.paymentapp.util.EnumUtil;

import java.util.Arrays;
import java.util.Optional;

public enum PreferredLanguage {
    ru_RU,
    en_US;

    public static Optional<PreferredLanguage> fromString(String value) {
        return Arrays.stream(values())
                .filter(lang -> lang.name().equalsIgnoreCase(value))
                .findFirst();
    }

    public static Optional<PreferredLanguage> find(String role) {
        return EnumUtil.find(PreferredLanguage.class, role);
    }
}
