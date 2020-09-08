package ecommerce.dao.interfaces;

import ecommerce.dao.GenericDao;
import ecommerce.model.Order;
import java.util.List;

public interface OrderDao extends GenericDao<Order, Long> {

    List<Order> getUserOrders(Long userId);

}
