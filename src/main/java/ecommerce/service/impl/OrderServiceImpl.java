package ecommerce.service.impl;

import ecommerce.dao.interfaces.OrderDao;
import ecommerce.lib.Inject;
import ecommerce.lib.Service;
import ecommerce.model.Order;
import ecommerce.model.ShoppingCart;
import ecommerce.service.interfaces.OrderService;
import ecommerce.service.interfaces.ShoppingCartService;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderDao orderDao;

    @Inject
    private ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        Order order = new Order(shoppingCart.getUserId());
        order.setProducts(List.copyOf(shoppingCart.getProducts()));
        shoppingCartService.clear(shoppingCart);
        return order;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderDao.getUserOrders(userId);
    }

    @Override
    public Order create(Order item) {
        return orderDao.create(item);
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
    public Order update(Order item) {
        return orderDao.update(item);
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.delete(id);
    }
}
