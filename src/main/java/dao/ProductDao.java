package dao;

import model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Product create(Product product);

    Optional<Product> get(Long id);

    List<Product> getAllProducts();

    Product update(Product product);

    boolean delete(Long id);
}
