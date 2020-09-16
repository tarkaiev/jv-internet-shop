package ecommerce.controllers.user;

import ecommerce.lib.Injector;
import ecommerce.model.Product;
import ecommerce.model.ShoppingCart;
import ecommerce.service.interfaces.ShoppingCartService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cart/current")
public class GetUserCartController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector injector = Injector.getInstance("ecommerce");
    private final ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        ShoppingCart currentShoppingCart = shoppingCartService.getByUserId(userId);
        List<Product> products = currentShoppingCart.getProducts();
        req.setAttribute("products", products);
        req.setAttribute("shoppingCartId", currentShoppingCart.getId());
        req.getRequestDispatcher("/WEB-INF/views/cart/shoppingCart.jsp").forward(req, resp);
    }
}
