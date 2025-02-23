package Java.com.paymentapp.service;

import Java.com.paymentapp.dao.PaymentDAO;
import Java.com.paymentapp.dto.PaymentDTO;
import Java.com.paymentapp.dto.PaymentReadDTO;
import Java.com.paymentapp.entity.Payment.PaymentEntity;
import Java.com.paymentapp.exception.validation.ValidationException;
import Java.com.paymentapp.exception.validation.ValidationResult;
import Java.com.paymentapp.mapper.PaymentMapper;
import Java.com.paymentapp.service.vaidations.PaymentValidator.PaymentValidator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;

@RequiredArgsConstructor
public class PaymentService {
    private final PaymentDAO paymentDAO;
    private final PaymentValidator paymentValidator;
    private final PaymentMapper paymentMapper;

    public void createPayment(PaymentDTO paymentDTO) {
        ValidationResult validation = paymentValidator.validate(paymentDTO);
        if (!validation.isValid()) {
            throw new ValidationException(validation.getErrors());
        }
        paymentDAO.save(paymentMapper.toEntity(paymentDTO));
    }

    @SneakyThrows
    public List<PaymentReadDTO> getLastTransactions(int userId) {
        return paymentDAO.findByUserId(userId);
    }

    public List<PaymentEntity> getLastPayments(int userId, int limit) {
        return paymentDAO.getLastPayments(userId, limit);
    }
}
