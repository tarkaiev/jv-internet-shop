package ecommerce.service.interfaces;

import ecommerce.model.Order;
import ecommerce.model.ShoppingCart;
import ecommerce.service.GenericService;
import java.util.List;

public interface OrderService extends GenericService<Order, Long> {
    Order completeOrder(ShoppingCart shoppingCart);

    List<Order> getUserOrders(Long userId);

    Double getTotalSum(Order order);
}
