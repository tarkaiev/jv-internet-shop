package ecommerce.controllers.order;

import ecommerce.lib.Injector;
import ecommerce.model.Order;
import ecommerce.service.interfaces.OrderService;
import ecommerce.service.interfaces.ShoppingCartService;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/order/complete")
public class CompleteOrderController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector injector = Injector.getInstance("ecommerce");
    private final OrderService orderService
            = (OrderService) injector.getInstance(OrderService.class);
    private final ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        Order order = orderService.completeOrder(shoppingCartService.getByUserId(userId));
        resp.sendRedirect(req.getContextPath() + "/user/order?id=" + order.getId());
    }
}
