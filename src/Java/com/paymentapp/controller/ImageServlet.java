package Java.com.paymentapp.controller;

import Java.com.paymentapp.service.FileStorageService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {
    private FileStorageService fileStorageService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        this.fileStorageService = (FileStorageService) context.getAttribute("fileStorageService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String imagePath = uri.replace("/images", "");

        fileStorageService.getImage(imagePath).ifPresentOrElse(
                inputStream -> {
                    resp.setContentType("application/octet-stream");
                    writeImageToResponse(inputStream, resp);
                }, () -> resp.setStatus(HttpServletResponse.SC_NOT_FOUND)
        );
    }

    @SneakyThrows
    private static void writeImageToResponse(InputStream inputStream, HttpServletResponse resp) {
        try (inputStream;
             ServletOutputStream outputStream = resp.getOutputStream()) {
            int currByte = 0;

            while ((currByte = inputStream.read()) != -1) {
                outputStream.write(currByte);
            }
        }
    }
}
