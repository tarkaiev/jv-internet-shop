package ecommerce.security;

import ecommerce.exceptions.AuthenticationException;
import ecommerce.model.User;

public interface AuthenticationService {
    User login(String login, String password)
            throws AuthenticationException;
}
