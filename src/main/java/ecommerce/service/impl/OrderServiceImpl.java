package ecommerce.service.impl;

import ecommerce.dao.OrderDao;
import ecommerce.lib.Inject;
import ecommerce.lib.Service;
import ecommerce.model.Order;
import ecommerce.model.ShoppingCart;
import ecommerce.service.OrderService;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    OrderDao orderDao;

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        Order order = orderDao.completeOrder(shoppingCart);
        order.setProducts(shoppingCart.getProducts());
        shoppingCart.getProducts().removeAll(shoppingCart.getProducts());
        return order;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderDao.getUserOrders(userId);
    }

    @Override
    public Order get(Long id) {
        return orderDao.get(id).get();
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.delete(id);
    }
}
