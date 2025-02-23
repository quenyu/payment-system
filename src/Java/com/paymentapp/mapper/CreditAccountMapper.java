package Java.com.paymentapp.mapper;

import Java.com.paymentapp.dto.CreditCardDTO;
import Java.com.paymentapp.dto.CreditCardReadDTO;
import Java.com.paymentapp.entity.CreditCard.CreditCardEntity;

public class CreditAccountMapper implements Mapper<CreditCardDTO, CreditCardEntity> {
    private static final CreditAccountMapper INSTANCE = new CreditAccountMapper();

    @Override
    public CreditCardEntity toEntity(CreditCardDTO from) {
        return new CreditCardEntity(
                from.getId(),
                from.getAccountId(),
                from.getCardNumber(),
                from.getCreditLimit(),
                from.getCurrentUsage(),
                from.getStatus(),
                from.getCreatedAt()
        );
    }

    public CreditCardReadDTO convertToDTO(CreditCardEntity entity) {
        return CreditCardReadDTO.builder()
                .id(entity.getId())
                .last4Digits(entity.getCardNumber().substring(15))
                .status(entity.getStatus())
                .build();
    }

    public static CreditAccountMapper getInstance() {
        return  INSTANCE;
    }
}
