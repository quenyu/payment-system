package Java.com.paymentapp.service;

import Java.com.paymentapp.dao.AccountDAO;
import Java.com.paymentapp.dao.CreditCardDAO;
import Java.com.paymentapp.dao.UserDAO;
import Java.com.paymentapp.entity.Account.AccountEntity;
import Java.com.paymentapp.entity.User.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReportService {
    private final UserDAO userDao;
    private final AccountDAO accountDao;

    @SneakyThrows
    public void generateUserReport(int userId, OutputStream outputStream) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                PDType1Font fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
                PDType1Font fontRegular = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

                contentStream.setFont(fontBold, 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 750);
                contentStream.showText("User Report");
                contentStream.endText();

                UserEntity user = userDao.findById(userId).orElseThrow();
                addSection(contentStream, "User Information", List.of(
                        "Name: " + user.getFirstName() + " " + user.getLastName(),
                        "Email: " + user.getEmail(),
                        "Registration Date: " + user.getCreatedAt().toLocalDateTime().format(DateTimeFormatter.ISO_DATE)
                ), 700, fontRegular, 12);

                List<AccountEntity> accounts = accountDao.findByUserId(userId);
                addSection(contentStream, "Bank Accounts (" + accounts.size() + ")",
                        accounts.stream()
                                .map(acc -> String.format("%s: %s (Balance: %.2f)",
                                        acc.getAccountNumber(),
                                        acc.getStatus(),
                                        acc.getBalance()))
                                .collect(Collectors.toList()),
                        600, fontRegular, 12);
            }

            document.save(outputStream);
        }
    }

    @SneakyThrows
    private void addSection(PDPageContentStream contentStream,
                            String title,
                            List<String> contentLines,
                            float startY,
                            PDType1Font contentFont,
                            float fontSize) {
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), fontSize + 2);
        contentStream.beginText();
        contentStream.newLineAtOffset(100, startY);
        contentStream.showText(title);
        contentStream.endText();

        contentStream.setFont(contentFont, fontSize);
        float currentY = startY - 25;
        for (String line : contentLines) {
            contentStream.beginText();
            contentStream.newLineAtOffset(120, currentY);
            contentStream.showText(line);
            contentStream.endText();
            currentY -= 18;
        }
    }
}