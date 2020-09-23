package ecommerce.dao.impl.jdbc;

import ecommerce.dao.interfaces.OrderDao;
import ecommerce.exceptions.DataProcessingException;
import ecommerce.lib.Dao;
import ecommerce.model.Order;
import ecommerce.model.Product;
import ecommerce.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    @Override
    public List<Order> getUserOrders(Long userId) {
        String query = "SELECT * FROM orders WHERE user_id = ? AND deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Order> allOrders = new ArrayList<>();
                while (resultSet.next()) {
                    Order order = getOrderFromResultSet(resultSet, connection);
                    allOrders.add(order);
                }
                return allOrders;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get order of user with id " + userId, e);
        }
    }

    @Override
    public Order create(Order order) {
        String query = "INSERT INTO orders (user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(query,
                         Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                order.setId(resultSet.getLong(1));
                statement.close();
                addOrderProducts(order, connection);
                return order;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create " + order, e);
        }
    }

    @Override
    public Optional<Order> get(Long id) {
        String query = "SELECT * FROM orders WHERE order_id = ? AND deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Order order = getOrderFromResultSet(resultSet, connection);
                    return Optional.of(order);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get order with ID " + id, e);
        }
    }

    @Override
    public List<Order> getAll() {
        String query = "SELECT * FROM orders WHERE deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
            List<Order> allOrders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = getOrderFromResultSet(resultSet, connection);
                allOrders.add(order);
            }
            return allOrders;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all orders", e);
        }
    }

    @Override
    public Order update(Order order) {
        String updateOrderQuery = "UPDATE orders SET user_id = ? WHERE order_id = ? AND deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(updateOrderQuery)) {
            statement.setLong(1, order.getUserId());
            statement.setLong(2, order.getId());
            statement.executeUpdate();
            statement.close();
            deleteOrderFromOrdersProducts(order.getId(), connection);
            addOrderProducts(order, connection);
            return order;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update " + order, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE orders SET deleted = TRUE WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(query)) {
            deleteOrderFromOrdersProducts(id,connection);
            statement.setLong(1, id);
            int numberOfRowsDeleted = statement.executeUpdate();
            return numberOfRowsDeleted != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete order with ID " + id, e);
        }
    }

    private void addOrderProducts(Order order, Connection connection) throws SQLException {
        String query
                = "INSERT INTO orders_products (order_id, product_id) VALUES (?, ?);";
        try (PreparedStatement insertStatement
                     = connection.prepareStatement(query)) {
            for (Product product : order.getProducts()) {
                insertStatement.setLong(1, order.getId());
                insertStatement.setLong(2, product.getId());
                insertStatement.executeUpdate();
            }
        }
    }

    private Order getOrderFromResultSet(ResultSet resultSet, Connection connection)
            throws SQLException {
        Long id = resultSet.getLong("order_id");
        Long userId = resultSet.getLong("user_id");
        Order order = new Order(getProductsFromOrderId(id, connection), userId);
        order.setId(id);
        return order;
    }

    private List<Product> getProductsFromOrderId(Long orderId, Connection connection)
            throws SQLException {
        String query = "SELECT products.* FROM orders_products "
                + "JOIN products USING (product_id) WHERE order_id = ? AND deleted = FALSE;";
        try (PreparedStatement statement
                     = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Product> products = new ArrayList<>();
                while (resultSet.next()) {
                    Long id = resultSet.getLong("product_id");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    Product product = new Product(name, price);
                    product.setId(id);
                    products.add(product);
                }
                return products;
            }
        }
    }

    private void deleteOrderFromOrdersProducts(Long orderId, Connection connection)
            throws SQLException {
        String query = "DELETE FROM orders_products WHERE order_id = ?;";
        try (PreparedStatement statement
                     = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            statement.executeUpdate();
        }
    }
}
