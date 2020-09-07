package ecommerce.dao.impl;

import ecommerce.dao.OrderDao;
import ecommerce.db.Storage;
import ecommerce.model.Order;
import ecommerce.model.ShoppingCart;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderDaoImpl implements OrderDao {

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        Order order = new Order(shoppingCart.getUserId());
        Storage.addOrder(order);
        return order;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return Storage.orders.stream()
                .filter(order -> order.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Order> get(Long id) {
        return Storage.orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.orders.removeIf(order -> order.getId().equals(id));
    }
}
