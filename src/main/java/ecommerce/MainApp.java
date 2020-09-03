package ecommerce;

import ecommerce.lib.Injector;
import ecommerce.model.Product;
import ecommerce.service.ProductService;

public class MainApp {
    private static Injector injector = Injector.getInstance("ecommerce");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        productService.create(new Product("iphone1", 999.99));
        productService.create(new Product("iphone2", 1999.99));
        productService.create(new Product("iphone3", 2999.99));

        System.out.println("all products after creation:");
        for (Product product : productService.getAllProducts()) {
            System.out.println(product);
        }

        System.out.println("updating price for iphone1");
        Product iphone1 = new Product(productService.get(1));
        iphone1.setPrice(499.99);
        productService.update(iphone1);
        System.out.println(productService.get(1));

        System.out.println("deleting iphone1");
        productService.delete(1);

        for (Product product : productService.getAllProducts()) {
            System.out.println(product);
        }

    }
}
