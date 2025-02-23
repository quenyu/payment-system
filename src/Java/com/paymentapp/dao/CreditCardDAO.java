package Java.com.paymentapp.dao;

import Java.com.paymentapp.dto.AdminCreditCardDTO;
import Java.com.paymentapp.entity.CreditCard.CreditCardEntity;
import Java.com.paymentapp.entity.CreditCard.CreditCardStatus;
import Java.com.paymentapp.entity.User.UserEntity;
import Java.com.paymentapp.util.DBConnection;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreditCardDAO implements DAO<Integer, CreditCardEntity> {
    private static final CreditCardDAO INSTANCE = new CreditCardDAO();

    private static final String FIND_BY_ID_SQL = """
            SELECT id, account_id, card_number, credit_limit, current_usage, status, created_at
            FROM credit_cards
            WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO credit_cards (account_id, card_number, credit_limit, current_usage, status, created_at)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM credit_cards
            WHERE id = ?
            """;

    private static final String UPDATE_BY_ID_SQL = """
            UPDATE credit_cards
            SET account_id = ?,
                card_number = ?,
                credit_limit = ?,
                current_usage = ?,
                status = ?,
                created_at = ?
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, account_id, card_number, credit_limit, current_usage, status, created_at
            FROM credit_cards
            """;

    private static final String FIND_BY_ACCOUNT_ID_SQL = """
            SELECT id, account_id, card_number, credit_limit, current_usage, status, created_at
            FROM credit_cards
            WHERE account_id = ?
            """;

    private static final String FIND_ALL_ADMIN_SQL = """
        SELECT
            cc.id,
            cc.card_number,
            cc.status,
            cc.created_at,
            u.email,
            a.account_number
        FROM credit_cards cc
        JOIN accounts a ON cc.account_id = a.id
        JOIN users u ON a.user_id = u.id
        """;


    @SneakyThrows
    public List<CreditCardEntity> findByAccountId(Integer accountId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ACCOUNT_ID_SQL)
        ) {
            ps.setObject(1, accountId);

            ResultSet rs = ps.executeQuery();
            List<CreditCardEntity> creditCards = new ArrayList<>();

            while (rs.next()) {
                creditCards.add(buildCreditCard(rs));
            }

            return creditCards;
        }
    }

    @Override
    @SneakyThrows
    public Optional<CreditCardEntity> findById(Integer id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)
        ) {
            ps.setObject(1, id);

            ResultSet rs = ps.executeQuery();
            CreditCardEntity user = null;

            if (rs.next()) {
                user = buildCreditCard(rs);
            }

            return Optional.ofNullable(user);
        }
    }

    @Override
    @SneakyThrows
    public CreditCardEntity save(CreditCardEntity creditCard) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, creditCard.getAccountId());
            ps.setObject(2, creditCard.getCardNumber());
            ps.setObject(3, creditCard.getCreditLimit());
            ps.setObject(4, creditCard.getCurrentUsage());
            ps.setObject(5, creditCard.getStatus().name());
            ps.setObject(6, creditCard.getCreatedAt());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            creditCard.setId(generatedKeys.getObject("id", Integer.class));

            return creditCard;
        }
    }

    @Override
    @SneakyThrows
    public CreditCardEntity update(CreditCardEntity creditCard) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_BY_ID_SQL)) {
            ps.setObject(1, creditCard.getAccountId());
            ps.setObject(2, creditCard.getCardNumber());
            ps.setObject(3, creditCard.getCreditLimit());
            ps.setObject(4, creditCard.getCurrentUsage());
            ps.setObject(5, creditCard.getStatus().name());
            ps.setObject(6, creditCard.getCreatedAt());

            ps.setObject(7, creditCard.getId());
            int affectedRows = ps.executeUpdate();

            return affectedRows > 0 ? creditCard : null;
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    @SneakyThrows
    public List<CreditCardEntity> findAll() {
        List<CreditCardEntity> users = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_SQL)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(buildCreditCard(rs));
            }
        }
        return users;
    }

    @SneakyThrows
    public List<AdminCreditCardDTO> findAllAdminCards() {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_ADMIN_SQL)) {
            List<AdminCreditCardDTO> result = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(buildAdminCreditCard(rs));
            }
            return result;
        }
    }

    @SneakyThrows
    private CreditCardEntity buildCreditCard(ResultSet rs) {
        return new CreditCardEntity(
                rs.getObject("id", Integer.class),
                rs.getObject("account_id", Integer.class),
                rs.getObject("card_number", String.class),
                rs.getObject("credit_limit", BigDecimal.class),
                rs.getObject("current_usage", BigDecimal.class),
                CreditCardStatus.valueOf(rs.getObject("status", String.class)),
                rs.getObject("created_at", Timestamp.class)
        );
    }

    @SneakyThrows
    private AdminCreditCardDTO buildAdminCreditCard(ResultSet rs) {
        return AdminCreditCardDTO.builder().
                id(rs.getObject("id", Integer.class)).
                last4Digits(rs.getObject("card_number", String.class)).
                status(CreditCardStatus.valueOf(rs.getObject("status", String.class))).
                userEmail(rs.getObject("email", String.class)).
                accountNumber(rs.getObject("account_number", String.class)).
                createdAt(rs.getObject("created_at", Timestamp.class)).
                build();
    }

    public static CreditCardDAO getInstance() {
        return INSTANCE;
    }
}
