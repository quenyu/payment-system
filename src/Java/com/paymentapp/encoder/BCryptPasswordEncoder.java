package Java.com.paymentapp.encoder;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

@RequiredArgsConstructor
public final class BCryptPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}