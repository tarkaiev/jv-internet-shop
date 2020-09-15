package ecommerce.controllers.order;

import ecommerce.lib.Injector;
import ecommerce.service.interfaces.ProductService;
import ecommerce.service.interfaces.ShoppingCartService;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cart/delete")
public class DeleteProductFromCartController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector injector = Injector.getInstance("ecommerce");
    private final ProductService productService
            = (ProductService) injector.getInstance(ProductService.class);
    private final ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long productId = Long.parseLong(req.getParameter("id"));
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        shoppingCartService.deleteProduct(shoppingCartService.getByUserId(userId),
                productService.get(productId));
        resp.sendRedirect(req.getContextPath() + "/cart/current");
    }
}
