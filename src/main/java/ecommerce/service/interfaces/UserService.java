package ecommerce.service.interfaces;

import ecommerce.model.User;
import ecommerce.service.GenericService;
import java.util.Optional;

public interface UserService extends GenericService<User, Long> {
    Optional<User> findByLogin(String login);
}
