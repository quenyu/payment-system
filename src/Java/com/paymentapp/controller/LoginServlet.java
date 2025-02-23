package Java.com.paymentapp.controller;

import Java.com.paymentapp.entity.User.UserEntity;
import Java.com.paymentapp.service.UserService;
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
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        this.userService = (UserService) context.getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspUtil.getPath("login")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Optional<UserEntity> userOpt = userService.login(email, password);

        if (userOpt.isPresent()) {
            HttpSession session = req.getSession();
            session.setAttribute("user", userOpt.get());
            session.setAttribute("lang", userOpt.get().getPreferredLanguage().name());
            resp.sendRedirect(req.getContextPath() + "/profile");
        } else {
            req.setAttribute("error", "Неверный email или пароль");
            req.getRequestDispatcher(JspUtil.getPath("login")).forward(req, resp);
        }
    }
}
