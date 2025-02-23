package Java.com.paymentapp.mapper;

import Java.com.paymentapp.dto.UserDTO;
import Java.com.paymentapp.entity.User.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class UserMapper implements Mapper<UserDTO, UserEntity> {
    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserEntity toEntity(UserDTO user) {
        return new UserEntity(
                user.getId(),
                user.getEmail(),
                user.getImage(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                user.getPreferredLanguage(),
                user.getCreatedAt()
        );
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}
