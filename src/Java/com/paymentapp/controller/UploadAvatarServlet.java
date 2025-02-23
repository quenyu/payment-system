package Java.com.paymentapp.controller;

import Java.com.paymentapp.entity.User.UserEntity;
import Java.com.paymentapp.exception.validation.ValidationException;
import Java.com.paymentapp.service.FileStorageService;
import Java.com.paymentapp.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/upload/avatar")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
public class UploadAvatarServlet extends HttpServlet {
    private FileStorageService fileStorageService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        this.fileStorageService = (FileStorageService) context.getAttribute("fileStorageService");
        this.userService = (UserService) context.getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Part filePart = req.getPart("avatar");
        try {
            String savedFileName = fileStorageService.saveAvatar(filePart);
            UserEntity updatedUser = userService.updateUserAvatar(user.getId(), savedFileName);
            session.setAttribute("user", updatedUser);
        } catch (ValidationException e) {
            req.setAttribute("uploadError", "File validation failed: " + e.getErrors());
        }
        resp.sendRedirect(req.getContextPath() + "/profile");
    }
}
