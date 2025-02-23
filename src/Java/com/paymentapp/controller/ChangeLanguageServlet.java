package Java.com.paymentapp.controller;

import Java.com.paymentapp.entity.User.PreferredLanguage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/locale")
public class ChangeLanguageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang = req.getParameter("lang");

        lang = PreferredLanguage.fromString(lang)
                .map(PreferredLanguage::name)
                .orElse(PreferredLanguage.en_US.name());
        req.getSession().setAttribute("lang", lang);

        String prevPage = req.getHeader("referer");

        if (prevPage != null && prevPage.contains("?lang=")) {
            prevPage = prevPage.replaceAll("\\?lang=[^&]*", "");
        }
        String toPage = prevPage != null ? prevPage : req.getContextPath() + "/";
        resp.sendRedirect(toPage);
    }
}
