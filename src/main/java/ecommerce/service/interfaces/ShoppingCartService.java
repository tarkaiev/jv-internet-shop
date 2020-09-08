package ecommerce.service.interfaces;

import ecommerce.model.Product;
import ecommerce.model.ShoppingCart;
import ecommerce.service.GenericService;

public interface ShoppingCartService extends GenericService<ShoppingCart, Long> {

    ShoppingCart addProduct(ShoppingCart shoppingCart, Product product);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);

    void clear(ShoppingCart shoppingCart);

    ShoppingCart getByUserId(Long userId);

}
