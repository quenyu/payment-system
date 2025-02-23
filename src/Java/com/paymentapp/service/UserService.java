package Java.com.paymentapp.service;

import Java.com.paymentapp.dao.UserDAO;
import Java.com.paymentapp.dto.AccountDTO;
import Java.com.paymentapp.dto.UserDTO;
import Java.com.paymentapp.encoder.BCryptPasswordEncoder;
import Java.com.paymentapp.entity.User.UserEntity;
import Java.com.paymentapp.exception.validation.ValidationException;
import Java.com.paymentapp.mapper.UserMapper;
import Java.com.paymentapp.service.vaidations.UserValidator.UserValidator;
import Java.com.paymentapp.exception.validation.ValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;
    private final UserValidator userValidator;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AccountService accountService;

    public Integer createUser(UserDTO userDTO) {
        ValidationResult validate = userValidator.validate(userDTO);
        if (!validate.isValid()) {
            throw new ValidationException(validate.getErrors());
        }

        UserEntity userEntity = userMapper.toEntity(userDTO);
        userEntity.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
        UserEntity savedUser = userDAO.save(userEntity);

        accountService.createAccount(savedUser.getId(), "RUB");

        return savedUser.getId();
   }

   public Optional<UserEntity> login(String email, String password) {
       return userDAO.findByEmail(email)
               .filter(user -> passwordEncoder.matches(password, user.getPasswordHash()));
   }

   @SneakyThrows
   public void setUserToAttribute(HttpServletRequest req, HttpServletResponse resp) {
       Integer currUserId = (Integer) req.getSession().getAttribute("currUserId");
       if (currUserId == null) {
           resp.sendRedirect(req.getContextPath() + "/login");
           return;
       }

       Optional<UserEntity> userOpt = getUser(currUserId);
       if (userOpt.isEmpty()) {
           resp.sendRedirect(req.getContextPath() + "/registration");
           throw new RuntimeException("User not found");
       }

       req.setAttribute("user", userOpt.get());
   }

    public Optional<UserEntity> getUser(Integer id) {
        return userDAO.findById(id);
    }

    public UserEntity updateUserAvatar(Integer userId, String fileName) {

        return userDAO.findById(userId)
                .map(user -> {
                    user.setImage(fileName);
                    return userDAO.update(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserEntity> findAll() {
        return userDAO.findAll();
    }
}
