package ecommerce.dao;

import ecommerce.model.ShoppingCart;
import java.util.List;
import java.util.Optional;

public interface ShoppingCartDao {

    ShoppingCart create(ShoppingCart cart);

    Optional<ShoppingCart> get(Long id);

    List<ShoppingCart> getAll();

    ShoppingCart update(ShoppingCart cart);

    boolean delete(Long id);

}
