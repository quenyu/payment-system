package Java.com.paymentapp.config;

import Java.com.paymentapp.service.ServiceFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        context.setAttribute("userDAO", ServiceFactory.getUserDAO());
        context.setAttribute("accountDAO", ServiceFactory.getAccountDAO());
        context.setAttribute("creditCardDAO", ServiceFactory.getCreditCardDAO());
        context.setAttribute("orderDAO", ServiceFactory.getOrderDAO());
        context.setAttribute("paymentDAO", ServiceFactory.getPaymentDAO());

        context.setAttribute("fileStorageService", ServiceFactory.createFileStorageService());
        context.setAttribute("userService", ServiceFactory.createUserService());
        context.setAttribute("paymentService", ServiceFactory.createPaymentService());context.setAttribute("userService", ServiceFactory.createUserService());
        context.setAttribute("creditCardService", ServiceFactory.createCreditCardService());
        context.setAttribute("accountService", ServiceFactory.createAccountService());
        context.setAttribute("transferFundsService", ServiceFactory.createTransferFundsService());
        context.setAttribute("reportService", ServiceFactory.createReportService());
    }
}
