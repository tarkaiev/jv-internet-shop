package ecommerce.controllers;

import ecommerce.lib.Injector;
import ecommerce.service.interfaces.ProductService;
import ecommerce.service.interfaces.ShoppingCartService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cart/delete")
public class DeleteProductFromCartController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("ecommerce");
    private final ProductService productService
            = (ProductService) injector.getInstance(ProductService.class);
    private final ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("id"));
        shoppingCartService.deleteProduct(shoppingCartService.getByUserId(1L),
                productService.get(productId));
        resp.sendRedirect(req.getContextPath() + "/cart/current");
    }
}
