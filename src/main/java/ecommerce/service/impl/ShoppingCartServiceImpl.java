package ecommerce.service.impl;

import ecommerce.dao.interfaces.ProductDao;
import ecommerce.dao.interfaces.ShoppingCartDao;
import ecommerce.lib.Inject;
import ecommerce.lib.Service;
import ecommerce.model.Product;
import ecommerce.model.ShoppingCart;
import ecommerce.service.interfaces.ShoppingCartService;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Inject
    private ProductDao productDao;

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return shoppingCartDao.create(shoppingCart);
    }

    @Override
    public ShoppingCart get(Long id) {
        return shoppingCartDao.get(id).get();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return shoppingCartDao.getAll();
    }

    @Override
    public ShoppingCart update(ShoppingCart item) {
        return shoppingCartDao.update(item);
    }

    @Override
    public boolean delete(Long id) {
        return shoppingCartDao.delete(id);
    }

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.getProducts().add(product);
        shoppingCartDao.update(shoppingCart);
        return shoppingCart;
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        boolean remove = shoppingCart.getProducts().remove(product);
        shoppingCartDao.update(shoppingCart);
        return remove;
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getProducts().clear();
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return shoppingCartDao.getByUserId(userId).get();
    }

}
