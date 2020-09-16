package ecommerce.controllers.product;

import ecommerce.lib.Injector;
import ecommerce.model.Product;
import ecommerce.model.ShoppingCart;
import ecommerce.service.interfaces.ProductService;
import ecommerce.service.interfaces.ShoppingCartService;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/product/buy")
public class AddProductToCartController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector injector = Injector.getInstance("ecommerce");
    private final ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private final ProductService productService
            = (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long productId = Long.valueOf(req.getParameter("id"));
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        ShoppingCart currentCart = shoppingCartService.getByUserId(userId);
        Product currentProduct = productService.get(productId);
        shoppingCartService.addProduct(currentCart, currentProduct);

        resp.sendRedirect(req.getContextPath() + "/product/all");
    }
}
