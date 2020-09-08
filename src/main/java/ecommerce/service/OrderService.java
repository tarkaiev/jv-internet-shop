package ecommerce.service;

import ecommerce.model.Order;
import ecommerce.model.ShoppingCart;
import java.util.List;

public interface OrderService {

    Order completeOrder(ShoppingCart shoppingCart);

    List<Order> getUserOrders(Long userId);

    Order get(Long id);

    List<Order> getAll();

    boolean delete(Long id);
}
