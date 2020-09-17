package ecommerce.controllers;

import ecommerce.lib.Injector;
import ecommerce.model.Product;
import ecommerce.model.Role;
import ecommerce.model.User;
import ecommerce.service.interfaces.ProductService;
import ecommerce.service.interfaces.UserService;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/inject")
public class InjectDataController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("ecommerce");
    private final ProductService productService
            = (ProductService) injector.getInstance(ProductService.class);
    private final UserService userService =
            (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        productService.create(
                new Product("iPhone1", 999.99));
        productService.create(
                new Product("iPhone2", 1999.99));
        productService.create(
                new Product("iPhone3", 2999.99));
        User admin = new User("admin", "admin", "admin");
        admin.setRoles(Set.of(Role.of("ADMIN")));
        userService.create(admin);

        req.getRequestDispatcher("/WEB-INF/views/injected.jsp").forward(req, resp);
    }
}
