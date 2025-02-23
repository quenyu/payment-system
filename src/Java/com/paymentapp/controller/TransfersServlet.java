package Java.com.paymentapp.controller;

import Java.com.paymentapp.dto.PaymentDTO;
import Java.com.paymentapp.entity.Account.AccountEntity;
import Java.com.paymentapp.entity.User.UserEntity;
import Java.com.paymentapp.exception.validation.ValidationException;
import Java.com.paymentapp.service.AccountService;
import Java.com.paymentapp.service.PaymentService;
import Java.com.paymentapp.service.TransferFundsService;
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
import java.net.UnknownServiceException;
import java.util.List;


@WebServlet("/transfers")
public class TransfersServlet extends HttpServlet {
    private AccountService accountService;
    private PaymentService paymentService;
    private TransferFundsService transferFundsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        this.accountService = (AccountService) context.getAttribute("accountService");
        this.paymentService = (PaymentService) context.getAttribute("paymentService");
        this.transferFundsService = (TransferFundsService) context.getAttribute("transferFundsService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEntity user = (UserEntity) req.getSession().getAttribute("user");
        List<AccountEntity> accounts = accountService.getUserAccounts(user.getId());

        req.setAttribute("accounts", accounts);
        req.getRequestDispatcher(JspUtil.getPath("transfers")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int fromAccountId = Integer.parseInt(req.getParameter("fromAccount"));
            BigDecimal amount = new BigDecimal(req.getParameter("amount"));
            UserEntity user = (UserEntity) req.getSession().getAttribute("user");

            PaymentDTO payment = transferFundsService.transferFunds(
                    fromAccountId,
                    req.getParameter("toAccountNumber"),
                    amount,
                    user.getId()
            );

            paymentService.createPayment(payment);
            resp.sendRedirect(req.getContextPath() + "/transfers?success=true");

        } catch (UnknownServiceException e) {
            resp.sendRedirect(req.getContextPath() + "/transfers?error=database_error");
        } catch (ValidationException e) {
            UserEntity user = (UserEntity) req.getSession().getAttribute("user");
            req.setAttribute("errors", e.getErrors());
            reloadFormData(req, user);
            req.getRequestDispatcher(JspUtil.getPath("transfers")).forward(req, resp);
        }
    }

    private void reloadFormData(HttpServletRequest req, UserEntity user) {
        req.setAttribute("accounts", accountService.getUserAccounts(user.getId()));
        req.setAttribute("fromAccount", req.getParameter("fromAccount"));
        req.setAttribute("toAccountNumber", req.getParameter("toAccountNumber"));
        req.setAttribute("amount", req.getParameter("amount"));
    }
}