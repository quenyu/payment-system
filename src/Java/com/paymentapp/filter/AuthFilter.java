package Java.com.paymentapp.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final String[] PUBLIC_PATHS = {
            "/login",
            "/registration",
            "/locale",
            "/upload/avatar",
            "/images/"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return Arrays.stream(PUBLIC_PATHS)
                .anyMatch(path::startsWith);
    }
}