package ecommerce.controllers;

import ecommerce.lib.Injector;
import ecommerce.model.Product;
import ecommerce.service.interfaces.ProductService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/filler")
public class FillerController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("ecommerce");
    private final ProductService productService
            = (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        productService.create(
                new Product("iPhone1", 999.99));
        productService.create(
                new Product("iPhone2", 1999.99));
        productService.create(
                new Product("iPhone3", 2999.99));

        req.getRequestDispatcher("/WEB-INF/views/filler.jsp").forward(req, resp);
    }
}
