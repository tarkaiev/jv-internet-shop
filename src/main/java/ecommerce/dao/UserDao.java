package ecommerce.dao;

import ecommerce.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User createUser(User user);

    Optional<User> get (Long id);

    List<User> getAll();

    User update(User user);

    boolean delete(Long id);

}
