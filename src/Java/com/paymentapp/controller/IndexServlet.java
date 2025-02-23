package Java.com.paymentapp.controller;

import Java.com.paymentapp.dto.PaymentReadDTO;
import Java.com.paymentapp.entity.User.UserEntity;
import Java.com.paymentapp.service.AccountService;
import Java.com.paymentapp.service.PaymentService;
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

@WebServlet("/")
public class IndexServlet extends HttpServlet {
    private AccountService accountService;
    private PaymentService paymentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        this.accountService = (AccountService) context.getAttribute("accountService");
        this.paymentService = (PaymentService) context.getAttribute("paymentService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEntity user = (UserEntity) req.getSession().getAttribute("user");

        try {
            BigDecimal totalBalance = accountService.calculateTotalBalance(user.getId());
            req.setAttribute("totalBalance", totalBalance);

            List<PaymentReadDTO> transactions = paymentService.getLastTransactions(user.getId());
            req.setAttribute("transactions", transactions);

        } catch (Exception e) {
            req.setAttribute("error", "Ошибка загрузки данных");
        }

        req.getRequestDispatcher(JspUtil.getPath("index")).forward(req, resp);
    }
}

