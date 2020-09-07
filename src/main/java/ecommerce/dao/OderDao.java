package ecommerce.dao;

import ecommerce.model.Order;
import ecommerce.model.ShoppingCart;

import java.util.List;
import java.util.Optional;

public interface OderDao {

    Order completeOrder(ShoppingCart shoppingCart);

    List<Order> getUserOrders(Long userId);

    Optional<Order> get(Long id);

    List<Order> getAll();

    boolean delete(Long id);
}
