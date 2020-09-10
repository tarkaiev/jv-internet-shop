package ecommerce.controllers;

import ecommerce.lib.Injector;
import ecommerce.model.User;
import ecommerce.service.interfaces.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/all")
public class GetAllUsersController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("ecommerce");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<User> allUsers = userService.getAll();

        req.setAttribute("users", allUsers);
        req.getRequestDispatcher("/WEB-INF/views/user/all.jsp").forward(req, resp);
    }
}
