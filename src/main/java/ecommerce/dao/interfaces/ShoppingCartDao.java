package ecommerce.dao.interfaces;

import ecommerce.dao.GenericDao;
import ecommerce.model.ShoppingCart;
import java.util.Optional;

public interface ShoppingCartDao extends GenericDao<ShoppingCart, Long> {

    Optional<ShoppingCart> getByUserId(Long userId);

}
