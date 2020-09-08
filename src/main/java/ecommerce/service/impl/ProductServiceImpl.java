package ecommerce.service.impl;

import ecommerce.dao.ProductDao;
import ecommerce.lib.Inject;
import ecommerce.lib.Service;
import ecommerce.model.Product;
import ecommerce.service.ProductService;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Inject
    private ProductDao productDao;

    @Override
    public Product create(Product product) {
        return productDao.create(product);
    }

    @Override
    public Product get(long id) {
        return productDao.get(id).orElseThrow();
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.getAll();
    }

    @Override
    public Product update(Product product) {
        return productDao.update(product);
    }

    @Override
    public boolean delete(long id) {
        return productDao.delete(id);
    }
}
