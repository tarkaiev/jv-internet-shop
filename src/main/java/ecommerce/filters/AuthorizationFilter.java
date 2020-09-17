package ecommerce.filters;

import ecommerce.lib.Injector;
import ecommerce.model.Role;
import ecommerce.model.User;
import ecommerce.service.interfaces.UserService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationFilter implements Filter {
    private static final Injector injector = Injector.getInstance("ecommerce");
    private static final String USER_ID = "user_id";
    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private final Map<String, Set<Role.RoleName>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/user/all", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/user/delete", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/admin/order/all", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/admin/product/all", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/product/add", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/product/delete", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/order/delete", Set.of(Role.RoleName.ADMIN));

        protectedUrls.put("/order/complete", Set.of(Role.RoleName.USER));
        protectedUrls.put("/product/buy", Set.of(Role.RoleName.USER));
        protectedUrls.put("/user/orders", Set.of(Role.RoleName.USER));
        protectedUrls.put("/cart/current", Set.of(Role.RoleName.USER));
        protectedUrls.put("/cart/delete", Set.of(Role.RoleName.USER));
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String requestedUrl = req.getServletPath();
        if (!protectedUrls.containsKey(requestedUrl)) {
            filterChain.doFilter(req, resp);
            return;
        }
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        User user = userService.get(userId);
        if (isAuthorized(user, protectedUrls.get(requestedUrl))) {
            filterChain.doFilter(req,resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
        }
    }

    private boolean isAuthorized(User user, Set<Role.RoleName> authorizedRoles) {
        for (Role.RoleName role : authorizedRoles) {
            for (Role userRole : user.getRoles()) {
                if (role.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
