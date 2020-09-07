package ecommerce.service;

import ecommerce.model.Product;
import ecommerce.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart addProduct(ShoppingCart shoppingCart, Product product);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);

    void clear(ShoppingCart shoppingCart);

    ShoppingCart getByUserId(Long userId);

    boolean delete(ShoppingCart shoppingCart);
}
