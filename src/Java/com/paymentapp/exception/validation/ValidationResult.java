package Java.com.paymentapp.exception.validation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationResult {
    private final List<Error> errors = new ArrayList<>();

    public void addError(Error error) {
        this.errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public void merge(ValidationResult validationResult) {
        this.errors.addAll(validationResult.getErrors());
    }
}

