package ecommerce.dao;

import ecommerce.model.ShoppingCart;
import java.util.List;
import java.util.Optional;

public interface ShoppingCartDao extends GenericDao<ShoppingCart, Long> {

    Optional<ShoppingCart> getByUserId(Long userId);

}
