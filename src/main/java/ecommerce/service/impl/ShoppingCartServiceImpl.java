package ecommerce.service.impl;

import ecommerce.dao.ProductDao;
import ecommerce.dao.ShoppingCartDao;
import ecommerce.lib.Inject;
import ecommerce.lib.Service;
import ecommerce.model.Product;
import ecommerce.model.ShoppingCart;
import ecommerce.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    ShoppingCartDao shoppingCartDao;

    @Inject
    ProductDao productDao;

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return shoppingCartDao.create(shoppingCart);
    }

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.getProducts().add(productDao.get(product.getId()).get());
        return shoppingCart;
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        return shoppingCart.getProducts().remove(product);
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getProducts().removeAll(shoppingCart.getProducts());
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return shoppingCartDao.get(userId).get();
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        return shoppingCartDao.delete(shoppingCart.getId());
    }
}
