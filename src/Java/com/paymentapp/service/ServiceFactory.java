package Java.com.paymentapp.service;

import Java.com.paymentapp.dao.*;
import Java.com.paymentapp.encoder.BCryptPasswordEncoder;
import Java.com.paymentapp.mapper.AccountMapper;
import Java.com.paymentapp.mapper.CreditAccountMapper;
import Java.com.paymentapp.mapper.PaymentMapper;
import Java.com.paymentapp.mapper.UserMapper;
import Java.com.paymentapp.service.vaidations.AccountValidator.AccountValidator;
import Java.com.paymentapp.service.vaidations.ImageValidator.ImageValidator;
import Java.com.paymentapp.service.vaidations.PaymentValidator.PaymentValidator;
import Java.com.paymentapp.service.vaidations.TransferValidator.TransferValidator;
import Java.com.paymentapp.service.vaidations.UserValidator.UserValidator;
import Java.com.paymentapp.service.vaidations.Validator;
import jakarta.servlet.http.Part;
import lombok.Getter;

public class ServiceFactory {
    @Getter
    private static final UserDAO userDAO = UserDAO.getInstance();
    @Getter
    private static final AccountDAO accountDAO = AccountDAO.getInstance();
    @Getter
    private static final CreditCardDAO creditCardDAO = CreditCardDAO.getInstance();
    @Getter
    private static final OrderDAO orderDAO = OrderDAO.getInstance();
    @Getter
    private static final PaymentDAO paymentDAO = PaymentDAO.getInstance();

    @Getter
    private static final UserValidator userValidator = new UserValidator();
    @Getter
    private static final AccountValidator accountValidator = new AccountValidator();
    @Getter
    private static final TransferValidator transferValidator = new TransferValidator();
    @Getter
    private static final PaymentValidator paymentValidator = new PaymentValidator();

    @Getter
    private static final UserMapper userMapper = UserMapper.getInstance();
    @Getter
    private static final AccountMapper accountMapper = AccountMapper.getInstance();
    @Getter
    private static final PaymentMapper paymentMapper = PaymentMapper.getInstance();
    @Getter
    private static final CreditAccountMapper creditAccountMapper = CreditAccountMapper.getInstance();
    @Getter
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static FileStorageService createFileStorageService() {
        Validator<Part> imageValidator = new ImageValidator(5 * 1024 * 1024); // 5MB
        return new FileStorageService(imageValidator);
    }

    public static UserService createUserService() {
        return new UserService(userDAO, userValidator, userMapper, passwordEncoder, createAccountService());
    }

    public static AccountService createAccountService() {
        return new AccountService(userDAO, accountDAO, accountMapper, accountValidator);
    }

    public static CreditCardService createCreditCardService() {
        return new CreditCardService(creditCardDAO, accountDAO, creditAccountMapper);
    }

    public static PaymentService createPaymentService() {
        return new PaymentService(paymentDAO, paymentValidator, paymentMapper);
    }

    public static TransferFundsService createTransferFundsService() {
        return new TransferFundsService(createAccountService(), transferValidator);
    }

    public static ReportService createReportService() {
        return new ReportService(userDAO, accountDAO);
    }
}