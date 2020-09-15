package ecommerce.controllers.user;

import ecommerce.lib.Injector;
import ecommerce.service.interfaces.ShoppingCartService;
import ecommerce.service.interfaces.UserService;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteUser")
public class DeleteUserController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("ecommerce");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long userId = Long.valueOf(req.getParameter("userId"));
        userService.delete(userId);
        shoppingCartService.delete(shoppingCartService.getByUserId(userId).getId());
        resp.sendRedirect(req.getContextPath() + "/user/all");
    }
}
