package Java.com.paymentapp.controller;

import Java.com.paymentapp.entity.User.UserEntity;
import Java.com.paymentapp.service.ReportService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/download-report")
public class DownloadReport extends HttpServlet {
    private ReportService reportService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        this.reportService = (ReportService) context.getAttribute("reportService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Disposition", "attachment; filename=user_report.pdf");
        resp.setContentType("application/pdf");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        UserEntity user = (UserEntity) req.getSession().getAttribute("user");

        try {
            reportService.generateUserReport(user.getId(), resp.getOutputStream());
        } catch (IOException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
