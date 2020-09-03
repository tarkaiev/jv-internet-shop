package service;


import dao.ProductDao;
import lib.Inject;
import lib.Service;
import model.Product;
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
        return productDao.getAllProducts();
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
