package ecommerce.dao.impl.jdbc;

import ecommerce.dao.interfaces.ShoppingCartDao;
import ecommerce.exceptions.DataProcessingException;
import ecommerce.lib.Dao;
import ecommerce.model.Product;
import ecommerce.model.ShoppingCart;
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
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        String query = "SELECT * FROM shopping_carts WHERE user_id = ? AND deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    ShoppingCart shoppingCart = getShoppingCartFromResultSet(resultSet);
                    long id = shoppingCart.getId();
                    shoppingCart.setProducts(getProductsFromShoppingCartId(id, connection));
                    return Optional.of(shoppingCart);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping cart by user id " + userId, e);
        }
    }

    @Override
    public ShoppingCart create(ShoppingCart cart) {
        String insertShoppingCartQuery = "INSERT INTO shopping_carts (user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(insertShoppingCartQuery,
                         Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, cart.getUserId());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                cart.setId(resultSet.getLong(1));
                statement.close();
                addCartsProducts(cart, connection);
                return cart;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create " + cart, e);
        }
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String query = "SELECT * FROM shopping_carts WHERE cart_id = ? AND deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    ShoppingCart shoppingCart = getShoppingCartFromResultSet(resultSet);
                    shoppingCart.setProducts(getProductsFromShoppingCartId(id, connection));
                    return Optional.of(shoppingCart);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping cart with id " + id, e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        String query = "SELECT * FROM shopping_carts WHERE deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
            List<ShoppingCart> allCarts = new ArrayList<>();
            while (resultSet.next()) {
                ShoppingCart shoppingCart = getShoppingCartFromResultSet(resultSet);
                shoppingCart.setProducts(
                        getProductsFromShoppingCartId(shoppingCart.getId(), connection));
                allCarts.add(shoppingCart);
            }
            return allCarts;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all carts", e);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart cart) {
        String updateShoppingCartQuery = "UPDATE shopping_carts SET user_id = ? "
                + "WHERE cart_id = ? AND deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(updateShoppingCartQuery)) {
            statement.setLong(1, cart.getUserId());
            statement.setLong(2, cart.getId());
            statement.executeUpdate();
            deleteCartsProducts(cart.getId(), connection);
            addCartsProducts(cart, connection);
            return cart;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update " + cart, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE shopping_carts SET deleted = TRUE WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(query)) {
            deleteCartsProducts(id, connection);
            statement.setLong(1, id);
            int deleted = statement.executeUpdate();
            return deleted != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete cart with id " + id, e);
        }
    }

    private ShoppingCart getShoppingCartFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("cart_id");
        Long userId = resultSet.getLong("user_id");
        ShoppingCart shoppingCart = new ShoppingCart(userId);
        shoppingCart.setId(id);
        return shoppingCart;
    }

    private List<Product> getProductsFromShoppingCartId(Long shoppingCartId, Connection connection)
            throws SQLException {
        String query = "SELECT products.* FROM shopping_carts_products "
                + "JOIN products USING (product_id) WHERE cart_id = ? ;";
        try (PreparedStatement statement
                     = connection.prepareStatement(query)) {
            statement.setLong(1, shoppingCartId);
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

    private void addCartsProducts(ShoppingCart shoppingCart, Connection connection)
            throws SQLException {
        String insertCartsProductsQuery
                = "INSERT INTO shopping_carts_products (cart_id, product_id) VALUES (?, ?);";
        try (PreparedStatement insertStatement
                     = connection.prepareStatement(insertCartsProductsQuery)) {
            for (Product product : shoppingCart.getProducts()) {
                insertStatement.setLong(1, shoppingCart.getId());
                insertStatement.setLong(2, product.getId());
                insertStatement.executeUpdate();
            }
        }
    }

    private void deleteCartsProducts(Long shoppingCartId, Connection connection)
            throws SQLException {
        String deleteShoppingCartQuery = "DELETE FROM shopping_carts_products WHERE cart_id = ?;";
        try (PreparedStatement statement
                     = connection.prepareStatement(deleteShoppingCartQuery)) {
            statement.setLong(1, shoppingCartId);
            statement.executeUpdate();
        }
    }
}
