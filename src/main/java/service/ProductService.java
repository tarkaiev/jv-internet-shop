package service;

import java.util.List;
import model.Product;

public interface ProductService {
    Product create(Product product);

    Product get(long id);

    List<Product> getAllProducts();

    Product update(Product product);

    boolean delete(long id);
}
