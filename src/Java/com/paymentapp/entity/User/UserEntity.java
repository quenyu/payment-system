package Java.com.paymentapp.entity.User;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserEntity {
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    private String email;
    private String image;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private UserRole role;
    private PreferredLanguage preferredLanguage;
    private Timestamp createdAt;
}