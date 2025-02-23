package Java.com.paymentapp.exception.validation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private final List<Error> errors;

    public ValidationException(Error error) {
        this.errors = List.of(error);
    }

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}
