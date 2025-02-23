package Java.com.paymentapp.service.vaidations.UserValidator;

import Java.com.paymentapp.dao.UserDAO;
import Java.com.paymentapp.dto.UserDTO;
import Java.com.paymentapp.exception.validation.Error;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.service.vaidations.Validator;

public class UserValidator implements Validator<UserDTO> {
    private final UserDAO userDao = UserDAO.getInstance();
    private final PasswordValidator passwordValidator = new PasswordValidator();
    private final RoleValidator roleValidator = new RoleValidator();
    private final FirstNameValidator firstNameValidator = new FirstNameValidator();
    private final LastNameValidator lastNameValidator = new LastNameValidator();

    @Override
    public ValidationResult validate(UserDTO userDTO) {
        ValidationResult result = new ValidationResult();

        if (!isValidEmail(userDTO.getEmail())) {
            result.addError(Error.of("email", "Invalid email format"));
        } else if (userDao.existsByEmail(userDTO.getEmail())) {
            result.addError(Error.of("email", "Email already registered"));
        }

        result.merge(passwordValidator.validatePassword(userDTO.getPassword()));

        result.merge(firstNameValidator.validate(userDTO.getFirstName()));

        result.merge(lastNameValidator.validate(userDTO.getLastName()));

        result.merge(roleValidator.validateRole(String.valueOf(userDTO.getRole())));

        return result;
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
}
