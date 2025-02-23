package Java.com.paymentapp.dao;

import Java.com.paymentapp.dto.PaymentReadDTO;
import Java.com.paymentapp.entity.Payment.PaymentEntity;
import Java.com.paymentapp.entity.Payment.PaymentStatus;
import Java.com.paymentapp.util.DBConnection;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class PaymentDAO implements DAO<Integer, PaymentEntity> {
    private static final PaymentDAO INSTANCE = new PaymentDAO();

    private static final String FIND_BY_ID_SQL = """
            SELECT id, from_account_id, to_account_id, order_id, amount, payment_date, status
            FROM payments
            WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO payments (from_account_id, to_account_id, order_id, amount, status)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM payments
            WHERE id = ?
            """;

    private static final String UPDATE_BY_ID_SQL = """
            UPDATE payments
            SET from_account_id = ?,
                to_account_id = ?,
                order_id = ?,
                amount = ?,
                payment_date = ?,
                status = ?
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, from_account_id, to_account_id, order_id, amount, payment_date, status
            FROM payments
            """;

    private static final String FIND_LAST_PAYMENTS_BY_USER_ID_SQL = """
            SELECT p.id, p.from_account_id, p.to_account_id, p.order_id, p.amount, p.payment_date, p.status
            FROM payments p
            JOIN accounts a ON p.from_account_id = a.id OR p.to_account_id = a.id
            WHERE a.user_id = ?
            ORDER BY p.payment_date DESC
            LIMIT ?;
            """;

    @SneakyThrows
    public List<PaymentEntity> getLastPayments(int userId, int limit) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_LAST_PAYMENTS_BY_USER_ID_SQL)) {

            ps.setInt(1, userId);
            ps.setInt(2, limit);

            ResultSet rs = ps.executeQuery();
            List<PaymentEntity> result = new ArrayList<>();
            while (rs.next()) {
                result.add(buildPayment(rs));
            }
            return result;
        }
    }

    @SneakyThrows
    public List<PaymentReadDTO> findByUserId(int userId) throws SQLException {
        String SQL = """
        SELECT
            p.id,
            p.amount,
            p.payment_date,
            p.status,
            from_acc.account_number as from_account,
            to_acc.account_number as to_account,
            from_acc.currency,
            from_acc.user_id = ? as is_outgoing
        FROM payments p
        JOIN accounts from_acc ON p.from_account_id = from_acc.id
        JOIN accounts to_acc ON p.to_account_id = to_acc.id
        WHERE from_acc.user_id = ? OR to_acc.user_id = ?
        ORDER BY p.payment_date DESC
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL)) {

            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ps.setInt(3, userId);

            ResultSet rs = ps.executeQuery();
            List<PaymentReadDTO> result = new ArrayList<>();

            while (rs.next()) {
                result.add(PaymentReadDTO.builder()
                        .paymentDate(rs.getTimestamp("payment_date"))
                        .amount(rs.getBigDecimal("amount"))
                        .fromAccountNumber(rs.getString("from_account"))
                        .toAccountNumber(rs.getString("to_account"))
                        .currency(rs.getString("currency"))
                        .outgoing(rs.getBoolean("is_outgoing"))
                        .status(PaymentStatus.valueOf(rs.getString("status")))
                        .build());
            }
            return result;
        }
    }

    @Override
    @SneakyThrows
    public Optional<PaymentEntity> findById(Integer id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            PaymentEntity payment = null;
            if (rs.next()) {
                payment = buildPayment(rs);
            }
            return Optional.ofNullable(payment);
        }
    }

    @Override
    @SneakyThrows
    public PaymentEntity save(PaymentEntity payment) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            ps.setObject(1, payment.getFromAccountId());
            ps.setObject(2, payment.getToAccountId());
            ps.setObject(3, payment.getOrderId());
            ps.setObject(4, payment.getAmount());
            ps.setObject(5, payment.getStatus().name());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            payment.setId(generatedKeys.getObject("id", Integer.class));

            return payment;
        }
    }

    @Override
    @SneakyThrows
    public PaymentEntity update(PaymentEntity payment) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_BY_ID_SQL)) {
            ps.setObject(1, payment.getFromAccountId());
            ps.setObject(2, payment.getToAccountId());
            ps.setObject(3, payment.getOrderId());
            ps.setObject(4, payment.getAmount());
            ps.setObject(5, payment.getPaymentDate());
            ps.setObject(6, payment.getStatus().name());
            ps.setObject(7, payment.getId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0 ? payment : null;
        }
    }

    @Override
    @SneakyThrows
    public boolean deleteById(Integer id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_BY_ID_SQL)) {
            ps.setObject(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    @SneakyThrows
    public List<PaymentEntity> findAll() {
        List<PaymentEntity> payments = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                payments.add(buildPayment(rs));
            }
        }
        return payments;
    }

    @SneakyThrows
    private static PaymentEntity buildPayment(ResultSet rs) {
        return new PaymentEntity(
                rs.getObject("id", Integer.class),
                rs.getObject("from_account_id", Integer.class),
                rs.getObject("to_account_id", Integer.class),
                rs.getObject("order_id", Integer.class),
                rs.getObject("amount", BigDecimal.class),
                rs.getObject("payment_date", Timestamp.class),
                PaymentStatus.valueOf(rs.getObject("status", String.class))
        );
    }

    public static PaymentDAO getInstance() {
        return INSTANCE;
    }
}
