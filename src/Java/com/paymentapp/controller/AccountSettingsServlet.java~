package Java.com.paymentapp.controller;

import Java.com.paymentapp.entity.Account.AccountEntity;
import Java.com.paymentapp.entity.User.UserEntity;
import Java.com.paymentapp.exception.validation.ValidationException;
import Java.com.paymentapp.service.AccountService;
import Java.com.paymentapp.util.JspUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.nio.charset.StandardCharsets.*;

@WebServlet("/account-settings")
public class AccountSettingsServlet extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        this.accountService = (AccountService) context.getAttribute("accountService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("delete".equals(req.getParameter("action"))) {
            handleDelete(req, resp);
            return;
        }

        UserEntity user = (UserEntity) req.getSession().getAttribute("user");
        List<AccountEntity> accounts = accountService.getUserAccounts(user.getId());
        req.setAttribute("accounts", accounts);
        req.getRequestDispatcher(JspUtil.getPath("account-settings")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");

        String currency = req.getParameter("currency");

        try {
            accountService.createAccount(user.getId(), currency);
            resp.sendRedirect("account-settings?success=Account+created");
        } catch (ValidationException e) {
            resp.sendRedirect("account-settings?error=" + URLEncoder.encode(e.getMessage(), UTF_8));
        }
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        int accountId = Integer.parseInt(req.getParameter("id"));
        try {
            accountService.closeAccount(accountId, user.getId());
            resp.sendRedirect("account-settings?success=Account+deleted");
        } catch (ValidationException e) {
            resp.sendRedirect("account-settings?error=" + URLEncoder.encode(e.getMessage(), UTF_8));
        }
    }
}
