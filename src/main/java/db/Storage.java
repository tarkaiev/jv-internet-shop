package db;

import model.Order;
import model.Product;
import model.ShoppingCart;
import model.User;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<Product> products = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
    public static final List<ShoppingCart> shoppingCarts = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    private static long productId = 0;
    private static long orderId = 0;
    private static long shoppingCartId = 0;
    private static long userId = 0;

    public static void addProduct(Product product) {
        product.setId(++productId);
        products.add(product);
    }
    public static void addOrder(Order order) {
        order.setId(++orderId);
        orders.add(order);
    }

    public static void addShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setId(++shoppingCartId);
        shoppingCarts.add(shoppingCart);
    }

    public static void addUser(User user) {
        user.setId(++userId);
        users.add(user);
    }
}
