package ecommerce.dao.interfaces;

import ecommerce.dao.GenericDao;
import ecommerce.model.User;
import java.util.Optional;

public interface UserDao extends GenericDao<User, Long> {
    Optional<User> findByLogin(String login);
}
