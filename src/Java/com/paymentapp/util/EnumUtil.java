package Java.com.paymentapp.util;

import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public final class EnumUtil {
    public static <T extends Enum<T>> Optional<T> find(Class<T> enumClass, String value) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.name().equalsIgnoreCase(value))
                .findFirst();
    }
}
