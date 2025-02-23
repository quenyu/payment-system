package Java.com.paymentapp.filter;

import Java.com.paymentapp.entity.User.PreferredLanguage;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@WebFilter("/*")
public class LocaleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        String langParam = req.getParameter("lang");
        if (langParam != null) {
            PreferredLanguage.fromString(langParam).ifPresent(lang -> {
                session.setAttribute("lang", lang.name());
            });
        }

        if (session.getAttribute("lang") == null) {
            String browserLang = req.getLocale().getLanguage();
            PreferredLanguage defaultLang = Arrays.stream(PreferredLanguage.values())
                    .filter(lang -> lang.name().startsWith(browserLang))
                    .findFirst()
                    .orElse(PreferredLanguage.ru_RU);

            session.setAttribute("lang", defaultLang.name());
        }

        chain.doFilter(request, response);
    }
}