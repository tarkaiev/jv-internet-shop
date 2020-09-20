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
    public Product create(Product element) {
        String query = "INSERT INTO products (name, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement insert =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, element.getName());
            insert.setDouble(2, element.getPrice());
            insert.executeUpdate();
            ResultSet generatedKey = insert.getGeneratedKeys();
            while (generatedKey.next()) {
                element.setId(generatedKey.getLong(1));
            }
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Cant create product", e);
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM products WHERE product_id = ? AND deleted = 0";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement select = connection.prepareStatement(query);
            select.setLong(1, id);
            ResultSet productRow = select.executeQuery();
            if (productRow.next()) {
                Long productId = productRow.getLong("product_id");
                String name = productRow.getString("name");
                double price = productRow.getDouble("price");
                Product product = new Product(name, price);
                product.setId(productId);
                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get product", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products WHERE deleted = 0";
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement select = connection.prepareStatement(query);
            ResultSet productRow = select.executeQuery();
            while (productRow.next()) {
                Long productId = productRow.getLong("product_id");
                String name = productRow.getString("name");
                double price = productRow.getDouble("price");
                Product product = new Product(name, price);
                product.setId(productId);
                products.add(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get the product", e);
        }
        return products;
    }

    @Override
    public Product update(Product element) {
        String query = "UPDATE products SET name = ?, price = ? "
                + "WHERE product_id = ? AND deleted = 0";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement update = connection.prepareStatement(query);
            update.setString(1, element.getName());
            update.setDouble(2, element.getPrice());
            update.setLong(3, element.getId());
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update product", e);
        }
        return element;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE products SET deleted = 1 WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement delete = connection.prepareStatement(query);
            delete.setLong(1, id);
            int deletedRow = delete.executeUpdate();
            if (deletedRow == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update product", e);
        }
        return false;
    }
}
