package Java.com.paymentapp.service.vaidations.PaymentValidator;

import Java.com.paymentapp.dto.PaymentDTO;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.service.vaidations.Validator;
import Java.com.paymentapp.exception.validation.Error;

import java.math.BigDecimal;
import java.util.Objects;

public class PaymentValidator implements Validator<PaymentDTO> {
    @Override
    public ValidationResult validate(PaymentDTO payment) {
        ValidationResult result = new ValidationResult();

        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            result.addError(Error.of("amount", "Invalid amount"));
        }

        if (Objects.equals(payment.getFromAccountId(), payment.getToAccountId())) {
            result.addError(Error.of("accounts", "Cannot transfer to same account"));
        }

        return result;
    }
}
