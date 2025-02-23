package Java.com.paymentapp.service;

import Java.com.paymentapp.dao.AccountDAO;
import Java.com.paymentapp.dao.CreditCardDAO;
import Java.com.paymentapp.dto.AdminCreditCardDTO;
import Java.com.paymentapp.dto.CreditCardReadDTO;
import Java.com.paymentapp.entity.Account.AccountEntity;
import Java.com.paymentapp.entity.CreditCard.CreditCardEntity;
import Java.com.paymentapp.entity.CreditCard.CreditCardStatus;
import Java.com.paymentapp.exception.validation.Error;
import Java.com.paymentapp.exception.validation.ValidationException;
import Java.com.paymentapp.mapper.CreditAccountMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CreditCardService {
    private final CreditCardDAO creditCardDAO;
    private final AccountDAO accountDAO;
    private final CreditAccountMapper creditAccountMapper;

    public void adminBlockCard(String cardId) {

        CreditCardEntity card = creditCardDAO.findById(Integer.valueOf(cardId))
                .orElseThrow(() -> new ValidationException(Error.of("Card not found", "CARD_NOT_FOUND")));

        card.setStatus(CreditCardStatus.BLOCKED);
        creditCardDAO.update(card);
    }

    public void blockCard(int cardId, int userId) {
        CreditCardEntity card = creditCardDAO.findById(cardId)
                .orElseThrow(() -> new ValidationException(Error.of("Card not found", "CARD_NOT_FOUND")));

        AccountEntity account = accountDAO.findById(card.getAccountId())
                .orElseThrow(() -> new ValidationException(Error.of("Account not found", "ACCOUNT_NOT_FOUND")));

        if (account.getUserId() != userId) {
            throw new ValidationException(Error.of("Access denied", "ACCESS_DENIED"));
        }

        card.setStatus(CreditCardStatus.BLOCKED);
        creditCardDAO.update(card);
    }

    public List<CreditCardReadDTO> getUserCards(int userId) {
        List<AccountEntity> accounts = accountDAO.findByUserId(userId);

        return accounts.stream()
                .flatMap(account ->
                        creditCardDAO.findByAccountId(account.getId()).stream()
                )
                 .map(this::convertToDTO)
                 .toList();
    }

    private CreditCardReadDTO convertToDTO(CreditCardEntity entity) {
        return CreditCardReadDTO.builder()
                .id(entity.getId())
                .last4Digits(extractLast4Digits(entity.getCardNumber()))
                .status(entity.getStatus())
                .build();
    }

    public List<AdminCreditCardDTO> findAllAdminCards() {
        return creditCardDAO.findAllAdminCards();
    }

    public List<CreditCardEntity> findAll() {
        return creditCardDAO.findAll();
    }

    public Long getTotalBlockedAdminCards(List<AdminCreditCardDTO> allCards) {
        return allCards.stream()
                .filter(card -> card.getStatus().equals(CreditCardStatus.BLOCKED))
                .count();
    }

    public Long getTotalBlockedCards(List<CreditCardReadDTO> allCards) {
        return allCards.stream()
                .filter(card -> card.getStatus().equals(CreditCardStatus.BLOCKED))
                .count();
    }

    public Long getTotalActiveCards(List<CreditCardReadDTO> allCards) {
        return allCards.stream()
                .filter(card -> card.getStatus().equals(CreditCardStatus.ACTIVE))
                .count();
    }

    private String extractLast4Digits(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "0000";
        }
        return cardNumber.substring(cardNumber.length() - 4);
    }
}
