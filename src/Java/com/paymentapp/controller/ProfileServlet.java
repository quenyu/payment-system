package Java.com.paymentapp.controller;

import Java.com.paymentapp.dto.CreditCardReadDTO;
import Java.com.paymentapp.dto.PaymentReadDTO;
import Java.com.paymentapp.entity.Account.AccountEntity;
import Java.com.paymentapp.entity.Payment.PaymentEntity;
import Java.com.paymentapp.entity.User.UserEntity;
import Java.com.paymentapp.service.AccountService;
import Java.com.paymentapp.service.CreditCardService;
import Java.com.paymentapp.service.PaymentService;
import Java.com.paymentapp.service.UserService;
import Java.com.paymentapp.util.JspUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    UserService userService;
    AccountService accountService;
    PaymentService paymentService;
    CreditCardService creditCardService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        this.userService = (UserService) context.getAttribute("userService");
        this.accountService = (AccountService) context.getAttribute("accountService");
        this.paymentService = (PaymentService) context.getAttribute("paymentService");
        this.creditCardService = (CreditCardService) context.getAttribute("creditCardService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEntity user = (UserEntity) req.getSession().getAttribute("user");

        BigDecimal totalBalance = accountService.calculateTotalBalance(user.getId());
        List<AccountEntity> accounts = accountService.getUserAccounts(user.getId());
        List<CreditCardReadDTO> cards = creditCardService.getUserCards(user.getId());
        Long activeCardsCount = creditCardService.getTotalActiveCards(cards);
        List<PaymentEntity> lastPayment = paymentService.getLastPayments(user.getId(), 1);

        req.setAttribute("lastPayment", lastPayment.getFirst());
        req.setAttribute("activeCardsCount", activeCardsCount);
        req.setAttribute("totalBalance", totalBalance);
        req.setAttribute("accounts", accounts);
        req.getRequestDispatcher(JspUtil.getPath("profile")).forward(req, resp);
    }
}
