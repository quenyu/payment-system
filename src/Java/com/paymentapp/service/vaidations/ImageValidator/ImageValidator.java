package Java.com.paymentapp.service.vaidations.ImageValidator;

import Java.com.paymentapp.exception.validation.Error;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.service.vaidations.Validator;
import jakarta.servlet.http.Part;

import java.util.Set;

public class ImageValidator implements Validator<Part> {
    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/gif"
    );
    private final long maxFileSize;

    public ImageValidator(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    @Override
    public ValidationResult validate(Part filePart) {
        ValidationResult result = new ValidationResult();

        if (filePart == null || filePart.getSize() == 0) {
            result.addError(Error.of("avatar", "File is required"));
            return result;
        }

        if (filePart.getSize() > maxFileSize) {
            result.addError(Error.of("avatar", "File size exceeds maximum allowed"));
        }

        String contentType = filePart.getContentType();
        if (!ALLOWED_TYPES.contains(contentType)) {
            result.addError(Error.of("avatar", "Invalid file type"));
        }

        return result;
    }
}
