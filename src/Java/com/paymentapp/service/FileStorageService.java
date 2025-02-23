package Java.com.paymentapp.service;

import Java.com.paymentapp.exception.validation.ValidationException;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.service.vaidations.Validator;
import Java.com.paymentapp.util.PropertiesUtil;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.*;

@RequiredArgsConstructor
public class FileStorageService {
    private static final String IMG_BASE_PATH = PropertiesUtil.getProperty("img.base.path");
    private static final String IMG_UPLOAD_BASE_PATH = PropertiesUtil.getProperty("img.upload.base.path");// D:/FirstSteps/payment-system
    private final Validator<Part> imageValidator;

    @SneakyThrows
    public Optional<InputStream> getImage(String imgPath) {
        Path fullImgPath = Path.of(IMG_BASE_PATH, imgPath);

        return Files.exists(fullImgPath)
                ? Optional.of(Files.newInputStream(fullImgPath))
                : Optional.empty();
    }

    @SneakyThrows
    public String saveAvatar(Part filePart)  {
        ValidationResult validation = imageValidator.validate(filePart);
        if (!validation.isValid()) {
            throw new ValidationException(validation.getErrors());
        }
        String fileName = generateFileName(filePart);
        Path path = Paths.get(IMG_UPLOAD_BASE_PATH, fileName);

        try (InputStream is = filePart.getInputStream()) {
            Files.copy(is, path, REPLACE_EXISTING);
        }

        return fileName;
    }

    private String generateFileName(Part part) {
        return UUID.randomUUID() + "_" + part.getSubmittedFileName();
    }
}
