package Java.com.paymentapp.controller;

import Java.com.paymentapp.dto.AdminCreditCardDTO;
import Java.com.paymentapp.dto.CreditCardDTO;
import Java.com.paymentapp.dto.CreditCardReadDTO;
import Java.com.paymentapp.entity.CreditCard.CreditCardEntity;
import Java.com.paymentapp.entity.CreditCard.CreditCardStatus;
import Java.com.paymentapp.entity.User.UserEntity;
import Java.com.paymentapp.entity.User.UserRole;
import Java.com.paymentapp.exception.validation.ValidationException;
import Java.com.paymentapp.service.CreditCardService;
import Java.com.paymentapp.service.UserService;
import Java.com.paymentapp.util.JspUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;

    @WebServlet("/admin-block-card")
    public class AdminBlockCardServlet extends HttpServlet {
        private CreditCardService creditCardService;
        private UserService userService;

        @Override
        public void init(ServletConfig config) throws ServletException {
            super.init(config);
            ServletContext context = config.getServletContext();
            this.creditCardService = (CreditCardService) context.getAttribute("creditCardService");
            this.userService = (UserService) context.getAttribute("userService");
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            checkAdminAccess(req, resp);

            List<AdminCreditCardDTO> allCards = creditCardService.findAllAdminCards();
            List<UserEntity> allUsers = userService.findAll();
            Long totalBlockedCards = creditCardService.getTotalBlockedAdminCards(allCards);

            req.setAttribute("totalBlockedCards", totalBlockedCards);
            req.setAttribute("cards", allCards);
            req.setAttribute("users", allUsers);
            req.getRequestDispatcher(JspUtil.getPath("admin-block-card")).forward(req, resp);
        }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkAdminAccess(req, resp);

        String cardId = req.getParameter("cardId");

        try {
            creditCardService.adminBlockCard(cardId);
            resp.sendRedirect(req.getContextPath() + "/admin-block-card?success=card_blocked");
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }

    @SneakyThrows
    private void checkAdminAccess(HttpServletRequest req, HttpServletResponse resp) {
        UserEntity user = (UserEntity) req.getSession().getAttribute("user");
        if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
