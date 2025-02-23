package Java.com.paymentapp.dao;

import Java.com.paymentapp.entity.Order.OrderEntity;
import Java.com.paymentapp.entity.Order.OrderStatus;
import Java.com.paymentapp.util.DBConnection;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderDAO implements DAO<Integer, OrderEntity> {
    private static final OrderDAO INSTANCE = new OrderDAO();

    private static final String FIND_BY_ID_SQL = """
            SELECT id, user_id, order_date, amount, status
            FROM orders
            WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO orders (user_id, order_date, amount, status)
            VALUES (?, ?, ?, ?)
            """;

    private static final String UPDATE_BY_ID_SQL = """
            UPDATE orders
            SET user_id = ?,
                order_date = ?,
                amount = ?,
                status = ?
            WHERE id = ?
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM orders
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, user_id, order_date, amount, status
            FROM orders
            """;

    @Override
    @SneakyThrows
    public Optional<OrderEntity> findById(Integer id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            OrderEntity order = null;
            if (rs.next()) {
                order = buildOrder(rs);
            }
            return Optional.ofNullable(order);
        }
    }

    @Override
    @SneakyThrows
    public OrderEntity save(OrderEntity order) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, order.getUserId());
            ps.setObject(2, order.getOrderDate());
            ps.setObject(3, order.getAmount());
            ps.setObject(4, order.getStatus().name());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            order.setId(generatedKeys.getInt("id"));

            return order;
        }
    }

    @Override
    @SneakyThrows
    public OrderEntity update(OrderEntity order) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_BY_ID_SQL)) {
            ps.setObject(1, order.getUserId());
            ps.setObject(2, order.getOrderDate());
            ps.setObject(3, order.getAmount());
            ps.setObject(4, order.getStatus().name());
            ps.setObject(5, order.getId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0 ? order : null;
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
    public List<OrderEntity> findAll() {
        List<OrderEntity> orders = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                orders.add(buildOrder(rs));
            }
        }
        return orders;
    }

    @SneakyThrows
    private OrderEntity buildOrder(ResultSet rs) {
        return new OrderEntity(
                rs.getObject("id", Integer.class),
                rs.getObject("user_id", Integer.class),
                rs.getObject("order_date", Timestamp.class),
                rs.getObject("amount", BigDecimal.class),
                OrderStatus.valueOf(rs.getObject("status", String.class))
        );
    }

    public static OrderDAO getInstance() {
        return INSTANCE;
    }
}
