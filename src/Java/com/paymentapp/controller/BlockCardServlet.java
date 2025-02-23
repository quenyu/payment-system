package Java.com.paymentapp.controller;

import Java.com.paymentapp.dto.CreditCardReadDTO;
import Java.com.paymentapp.entity.User.UserEntity;
import Java.com.paymentapp.exception.validation.ValidationException;
import Java.com.paymentapp.service.CreditCardService;
import Java.com.paymentapp.util.JspUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/block-card")
public class BlockCardServlet extends HttpServlet {
    CreditCardService creditCardService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        this.creditCardService = (CreditCardService) context.getAttribute("creditCardService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEntity user = (UserEntity) req.getSession().getAttribute("user");
        List<CreditCardReadDTO> cards = creditCardService.getUserCards(user.getId());
        Long totalBlockedCards = creditCardService.getTotalBlockedCards(cards);

        req.setAttribute("totalBlockedCards", totalBlockedCards);
        req.setAttribute("cards", cards);
        req.getRequestDispatcher(JspUtil.getPath("block-card")).forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int cardId = Integer.parseInt(req.getParameter("cardId"));
        UserEntity user = ((UserEntity) req.getSession().getAttribute("user"));

        creditCardService.blockCard(cardId, user.getId());

        try {
            creditCardService.blockCard(cardId, user.getId());
            resp.sendRedirect(req.getContextPath() + "/block-card?success=card_blocked");
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}
