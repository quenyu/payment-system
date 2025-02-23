package Java.com.paymentapp.dto;

import Java.com.paymentapp.entity.User.PreferredLanguage;
import Java.com.paymentapp.entity.User.UserRole;
import lombok.Builder;
import lombok.Value;

import java.sql.Timestamp;

@Value
@Builder
public class UserDTO {
    Integer id;
    String email;
    String image;
    String password;
    String firstName;
    String lastName;
    UserRole role;
    PreferredLanguage preferredLanguage;
    Timestamp createdAt;
}