package ecommerce;

import ecommerce.lib.Injector;
import ecommerce.model.Product;
import ecommerce.model.ShoppingCart;
import ecommerce.model.User;
import ecommerce.service.OrderService;
import ecommerce.service.ProductService;
import ecommerce.service.ShoppingCartService;
import ecommerce.service.UserService;

public class MainApp {
    private static Injector injector = Injector.getInstance("ecommerce");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        System.out.println("TESTING PRODUCT SERVICE:");
        Product iphone1 = new Product("iphone1", 999.99);
        Product iphone2 = new Product("iphone2", 1999.99);
        Product iphone3 = new Product("iphone3", 2999.99);
        productService.create(iphone1);
        productService.create(iphone2);
        productService.create(iphone3);

        System.out.println("all products after creation:");
        for (Product product : productService.getAllProducts()) {
            System.out.println(product);
        }

        System.out.println("updating price for iphone1");
        Product iphoneUpdate = new Product(productService.get(1));
        iphoneUpdate.setPrice(499.99);
        productService.update(iphoneUpdate);
        System.out.println(productService.get(1));

        System.out.println("deleting iphone1");
        productService.delete(1);

        productService.getAllProducts().forEach(System.out::println);

        System.out.println("TESTING USER SERVICE");
        UserService userService = (UserService) injector.getInstance(UserService.class);

        userService.create(new User("Sasha", "sanek322", "qwerty"));
        userService.create(new User("Siroja", "serega228", "qwerty"));
        userService.create(new User("Valera", "valeros", "qwerty"));

        System.out.println("ALL USERS AFTER CREATION:");
        userService.getAll().forEach(System.out::println);

        System.out.println("DELETING SASHA");
        userService.delete(1L);
        System.out.println("ALL USERS AFTER DELETING SASHA");
        userService.getAll().forEach(System.out::println);

        System.out.println("UPDATING USERNAME FROM SIROJA TO SERGIO");
        User updateUser = new User("Sergio", "serega228", "qwerty");
        updateUser.setId(2L);
        userService.update(updateUser);
        userService.getAll().forEach(System.out::println);

        System.out.println("TESTING SHOPPING CART");
        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        ShoppingCart sergioCart = new ShoppingCart(2L);

        shoppingCartService.create(sergioCart);

        shoppingCartService.addProduct(sergioCart, iphone2);
        shoppingCartService.addProduct(sergioCart, iphone3);

        System.out.println(shoppingCartService.getByUserId(2L).getProducts());

        System.out.println("REMOVING ONE ITEM FROM CART");
        shoppingCartService.deleteProduct(sergioCart, iphone3);

        System.out.println(shoppingCartService.getByUserId(2L).getProducts());

        System.out.println("TESTING ORDER");
        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);

        orderService.completeOrder(sergioCart);

        System.out.println("ORDER COMPLETED");

        System.out.println(shoppingCartService.getByUserId(2L).getProducts());

    }
}
