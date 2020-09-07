package ecommerce.service.impl;

import ecommerce.dao.UserDao;
import ecommerce.lib.Inject;
import ecommerce.lib.Service;
import ecommerce.model.User;
import ecommerce.service.UserService;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    UserDao userDao;

    @Override
    public User create(User user) {
        return userDao.createUser(user);
    }

    @Override
    public User get(Long id) {
        return userDao.get(id).get();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }
}