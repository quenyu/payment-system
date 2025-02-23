package Java.com.paymentapp.dao;

import Java.com.paymentapp.entity.Account.AccountEntity;
import Java.com.paymentapp.entity.Account.AccountStatus;
import Java.com.paymentapp.util.DBConnection;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class AccountDAO implements DAO<Integer, AccountEntity> {
    private static final AccountDAO INSTANCE = new AccountDAO();

    private static final String FIND_BY_ID_SQL = """
            SELECT id, user_id, account_number, balance, status, created_at, currency
            FROM accounts
            WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO accounts (user_id, account_number, balance, status, currency)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM accounts
            WHERE id = ?
            """;

    private static final String UPDATE_BY_ID_SQL = """
            UPDATE accounts
            SET user_id = ?,
                account_number = ?,
                balance = ?,
                status = ?,
                currency = ?
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, user_id, account_number, balance, status, created_at, currency
            FROM accounts
            """;

    private static final String EXISTS_BY_ID_SQL = "SELECT EXISTS(SELECT 1 FROM accounts WHERE id = ?)";

    private static final String EXISTS_BY_USER_ID_SQL = "SELECT EXISTS(SELECT 1 FROM accounts WHERE user_id = ?)";

    private static final String EXISTS_BY_NUMBER_SQL = "SELECT EXISTS(SELECT 1 FROM accounts WHERE account_number = ?)";

    private static final String FIND_BY_USER_ID_SQL = "SELECT id, user_id, account_number, balance, status, created_at, currency FROM accounts WHERE user_id = ?";

    private static final String FIND_BY_ACCOUNT_NUMBER_SQL = "SELECT id, user_id, account_number, balance, status, created_at, currency FROM accounts WHERE account_number = ?";

    @SneakyThrows
    public BigDecimal getTotalBalanceByUserId(int userId) {
        String SQL = "SELECT SUM(balance) AS total FROM accounts WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("total");
            }
            return BigDecimal.ZERO;
        }
    }

    @SneakyThrows
    public boolean existsById(Integer id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(EXISTS_BY_ID_SQL)) {

            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getBoolean(1);
            }
        }
    }

    @SneakyThrows
    public boolean existsByUserId(Integer userId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(EXISTS_BY_USER_ID_SQL)) {

            ps.setObject(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getBoolean(1);
            }
        }
    }

    @SneakyThrows
    public boolean existsByAccountNumber(String accountNumber) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(EXISTS_BY_NUMBER_SQL)) {

            ps.setObject(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getBoolean(1);
            }
        }
    }
    @SneakyThrows
    private boolean accountExists(int accountId, Connection conn) throws SQLException {
        final String SQL = "SELECT 1 FROM accounts WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @SneakyThrows
    public List<AccountEntity> findByUserId(Integer userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_USER_ID_SQL)) {
            ps.setObject(1, userId);
            ResultSet rs = ps.executeQuery();
            List<AccountEntity> accounts = new ArrayList<>();
            while (rs.next()) {
                accounts.add(buildAccount(rs));
            }
            return accounts;
        }
    }

    @SneakyThrows
    public Optional<AccountEntity> findByAccountNumber(String accountNumber) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ACCOUNT_NUMBER_SQL)) {
            ps.setObject(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            AccountEntity account = null;
            if (rs.next()) {
                account = buildAccount(rs);
            }
            return Optional.ofNullable(account);
        }
    }

    @Override
    @SneakyThrows
    public Optional<AccountEntity> findById(Integer id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)
        ) {
            ps.setObject(1, id);

            ResultSet rs = ps.executeQuery();
            AccountEntity account = null;

            if (rs.next()) {
                account = buildAccount(rs);
            }

            return Optional.ofNullable(account);
        }
    }

    @Override
    @SneakyThrows
    public AccountEntity save(AccountEntity account) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, account.getUserId());
            ps.setObject(2, account.getAccountNumber());
            ps.setObject(3, account.getBalance());
            ps.setObject(4, account.getStatus().name());
            ps.setObject(5, account.getCurrency());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            account.setId(generatedKeys.getObject("id", Integer.class));

            return account;
        }
    }

    @Override
    @SneakyThrows
    public AccountEntity update(AccountEntity entity) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_BY_ID_SQL)) {
            ps.setObject(1, entity.getUserId());
            ps.setObject(2, entity.getAccountNumber());
            ps.setObject(3, entity.getBalance());
            ps.setObject(4, entity.getStatus().name());
            ps.setObject(5, entity.getCurrency());
            ps.setObject(6, entity.getId());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0 ? entity : null;
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
    public List<AccountEntity> findAll() {
        List<AccountEntity> accounts = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_SQL)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accounts.add(buildAccount(rs));
            }
        }
        return accounts;
    }

    @SneakyThrows
    public void transferFundsTransactionally(AccountEntity fromAccount, AccountEntity toAccount) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                updateInternal(fromAccount, conn);
                updateInternal(toAccount, conn);

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
            }
        }
    }

    private void updateInternal(AccountEntity entity, Connection conn) throws SQLException {
        final String UPDATE_SQL = """
        UPDATE accounts
        SET
            user_id = ?,
            account_number = ?,
            balance = ?,
            status = ?,
            currency = ?
        WHERE id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setInt(1, entity.getUserId());
            ps.setString(2, entity.getAccountNumber());
            ps.setBigDecimal(3, entity.getBalance());
            ps.setString(4, entity.getStatus().name());
            ps.setString(5, entity.getCurrency());
            ps.setInt(6, entity.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update failed, no rows affected for account: " + entity.getId());
            }
        }
    }

    @SneakyThrows
    private AccountEntity buildAccount(ResultSet rs) {
        return new AccountEntity(
                rs.getObject("id", Integer.class),
                rs.getObject("user_id", Integer.class),
                rs.getObject("account_number", String.class),
                rs.getObject("balance", BigDecimal.class),
                AccountStatus.valueOf(rs.getObject("status", String.class)),
                rs.getObject("created_at", Timestamp.class),
                rs.getObject("currency", String.class)
        );
    }

    public static AccountDAO getInstance() {
        return INSTANCE;
    }
}
