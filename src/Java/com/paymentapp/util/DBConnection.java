package Java.com.paymentapp.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;

@UtilityClass
public class DBConnection {
    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";

    static {
        loadDrivers();
    }

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(
                PropertiesUtil.getProperty(URL_KEY),
                PropertiesUtil.getProperty(USER_KEY),
                PropertiesUtil.getProperty(PASSWORD_KEY)
        );
    }

    @SneakyThrows
    private static void loadDrivers() {
        Class.forName("org.postgresql.Driver");
    }
}
