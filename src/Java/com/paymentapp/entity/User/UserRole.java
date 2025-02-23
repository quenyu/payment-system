package Java.com.paymentapp.entity.User;

import Java.com.paymentapp.util.EnumUtil;

import java.util.Optional;

public enum UserRole {
    CLIENT,
    ADMIN;

    public static Optional<UserRole> find(String role) {
        return EnumUtil.find(UserRole.class, role);
    }

    public static String formatRole(String role) {
        if (role == null || role.isEmpty()) {
            return role;
        }
        return role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase();
    }
}
