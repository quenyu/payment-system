package Java.com.paymentapp.service.vaidations;

import Java.com.paymentapp.exception.validation.ValidationResult;

public interface Validator<E> {
    ValidationResult validate(E entity);
}

