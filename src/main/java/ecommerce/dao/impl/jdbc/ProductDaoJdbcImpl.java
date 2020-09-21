package ecommerce.dao.impl.jdbc;

import ecommerce.dao.interfaces.ProductDao;
import ecommerce.exceptions.DataProcessingException;
import ecommerce.lib.Dao;
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
public class ProductDaoJdbcImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products (name, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement insert =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, product.getName());
            insert.setDouble(2, product.getPrice());
            insert.executeUpdate();
            ResultSet generatedKey = insert.getGeneratedKeys();
            while (generatedKey.next()) {
                product.setId(generatedKey.getLong(1));
            }
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Cant create product" + product.toString(), e);
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM products WHERE product_id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement select = connection.prepareStatement(query);
            select.setLong(1, id);
            ResultSet productRow = select.executeQuery();
            if (productRow.next()) {
                return Optional.of(getProductFromResultSet(productRow));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get product with id=" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products WHERE deleted = FALSE";
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement select = connection.prepareStatement(query);
            ResultSet productRow = select.executeQuery();
            while (productRow.next()) {
                Product product = getProductFromResultSet(productRow);
                products.add(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all products", e);
        }
        return products;
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products SET name = ?, price = ? "
                + "WHERE product_id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement update = connection.prepareStatement(query);
            update.setString(1, product.getName());
            update.setDouble(2, product.getPrice());
            update.setLong(3, product.getId());
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update product" + product.toString(), e);
        }
        return product;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE products SET deleted = TRUE WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement delete = connection.prepareStatement(query);
            delete.setLong(1, id);
            return delete.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update product with id=" + id, e);
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Long productId = resultSet.getLong("product_id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        return new Product(productId, name, price);
    }
}
