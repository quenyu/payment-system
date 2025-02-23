package Java.com.paymentapp.exception.validation;

import lombok.Value;

@Value(staticConstructor = "of")
public class Error {
    String message;
    String code;
}