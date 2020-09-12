package ecommerce.controllers;

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
public class CurrentCartController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector injector = Injector.getInstance("ecommerce");
    private final ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ShoppingCart currentShoppingCart = shoppingCartService.getByUserId(USER_ID);
        List<Product> products = currentShoppingCart.getProducts();
        req.setAttribute("products", products);
        req.setAttribute("shoppingCartId", shoppingCartService.getByUserId(1L).getId());
        req.getRequestDispatcher("/WEB-INF/views/cart/shoppingCart.jsp").forward(req, resp);
    }
}
