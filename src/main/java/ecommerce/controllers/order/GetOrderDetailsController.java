package ecommerce.controllers.order;

import ecommerce.lib.Injector;
import ecommerce.model.Order;
import ecommerce.service.interfaces.OrderService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/order")
public class GetOrderDetailsController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("ecommerce");
    private final OrderService orderService
            = (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long orderId = Long.parseLong(req.getParameter("id"));
        Order order = orderService.get(orderId);
        req.setAttribute("order", order);
        Double totalSum = orderService.getTotalSum(order);
        req.setAttribute("totalSum", totalSum);
        req.getRequestDispatcher("/WEB-INF/views/order/details.jsp").forward(req, resp);
    }
}
