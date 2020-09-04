package ecommerce.service;

import ecommerce.model.Product;
import java.util.List;

public interface ProductService {
    Product create(Product product);

    Product get(long id);

    List<Product> getAllProducts();

    Product update(Product product);

    boolean delete(long id);
}
