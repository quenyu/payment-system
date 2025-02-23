package Java.com.paymentapp.mapper;

import Java.com.paymentapp.dto.PaymentDTO;
import Java.com.paymentapp.entity.Payment.PaymentEntity;

public class PaymentMapper implements Mapper<PaymentDTO, PaymentEntity> {
    private static final PaymentMapper INSTANCE = new PaymentMapper();

    @Override
    public PaymentEntity toEntity(PaymentDTO from) {
        return new PaymentEntity(
                from.getId(),
                from.getFromAccountId(),
                from.getToAccountId(),
                from.getOrderId(),
                from.getAmount(),
                from.getPaymentDate(),
                from.getStatus()
        );
    }

//    public PaymentDTO toDTO(PaymentEntity entity,
//                            String fromAccountNumber,
//                            String toAccountNumber,
//                            String currency,
//                            AmountType amountType) {
//        return PaymentDTO.builder()
//                .paymentDate(entity.getPaymentDate())
//                .fromAccountNumber(fromAccountNumber)
//                .toAccountNumber(toAccountNumber)
//                .amount(entity.getAmount())
//                .currency(currency)
//                .status(entity.getStatus())
//                .amountType(amountType)
//                .build();
//    }

    public static PaymentMapper getInstance() {
        return INSTANCE;
    }
}
