package Java.com.paymentapp.controller;

import Java.com.paymentapp.dto.UserDTO;
import Java.com.paymentapp.entity.User.PreferredLanguage;
import Java.com.paymentapp.entity.User.UserEntity;
import Java.com.paymentapp.entity.User.UserRole;
import Java.com.paymentapp.exception.validation.ValidationException;
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
import java.util.List;
import java.util.Optional;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        this.userService = (UserService) context.getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserRole> roles = List.of(UserRole.CLIENT, UserRole.ADMIN);
        List<String> formattedRoles = roles.stream()
                .map(UserRole::name)
                .map(UserRole::formatRole)
                .toList();
        req.setAttribute("roles", formattedRoles);

        req.getRequestDispatcher(JspUtil.getPath("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String role = req.getParameter("role");
        String language = req.getParameter("language");

        UserDTO user = UserDTO.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .role(UserRole.find(role).orElseThrow(() -> new IllegalArgumentException("Invalid user role:" + role)))
                .preferredLanguage(PreferredLanguage.find(language).orElseThrow(() -> new IllegalArgumentException("Invalid language:" + language)))
                .build();

        try {
            Integer currUserId = userService.createUser(user);
            UserEntity createdUser = userService.getUser(currUserId).orElseThrow(() -> new RuntimeException("User not found after registration"));

            HttpSession session = req.getSession();
            session.setAttribute("user", createdUser);
            session.setAttribute("lang", user.getPreferredLanguage().name());
            resp.sendRedirect(req.getContextPath() + "/profile");
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}
