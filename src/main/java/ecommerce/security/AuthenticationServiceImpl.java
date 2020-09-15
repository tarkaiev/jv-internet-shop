package ecommerce.security;

import ecommerce.exceptions.AuthenticationException;
import ecommerce.lib.Inject;
import ecommerce.lib.Service;
import ecommerce.model.User;
import ecommerce.service.interfaces.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User userFromDB = userService.findByLogin(login)
                .orElseThrow(() -> new AuthenticationException("No such login exists"));
        if (userFromDB.getPassword().equals(password)) {
            return userFromDB;
        } else {
            throw new AuthenticationException("Incorrect password");
        }
    }
}
