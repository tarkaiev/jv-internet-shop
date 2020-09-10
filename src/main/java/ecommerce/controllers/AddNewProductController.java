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

@WebServlet("/product/add")
public class AddNewProductController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("ecommerce");
    private final ProductService productService
            = (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/product/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String price = req.getParameter("price");

        productService.create(new Product(name, Double.parseDouble(price)));
        resp.sendRedirect(req.getContextPath() + "/product/all");
    }
}
