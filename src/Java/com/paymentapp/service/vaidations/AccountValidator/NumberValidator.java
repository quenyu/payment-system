package Java.com.paymentapp.service.vaidations.AccountValidator;

import Java.com.paymentapp.exception.validation.Error;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.service.vaidations.Validator;

import java.util.regex.Pattern;

public class NumberValidator implements Validator<String> {
    private static final Pattern IBAN_PATTERN =
            Pattern.compile("^(RU|US)\\d{18}$");

    @Override
    public ValidationResult validate(String accountNumber) {
        ValidationResult result = new ValidationResult();

        if (!IBAN_PATTERN.matcher(accountNumber).matches()) {
            result.addError(Error.of("account_number",
                    "Invalid account number. Format: RU/US + 18 digits"));
        }

        return result;
    }
}
