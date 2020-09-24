package ecommerce.dao.impl.jdbc;

import ecommerce.dao.interfaces.UserDao;
import ecommerce.exceptions.DataProcessingException;
import ecommerce.lib.Dao;
import ecommerce.model.Role;
import ecommerce.model.User;
import ecommerce.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM users WHERE login = ? AND deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = getUserFromResultSet(resultSet);
                    user.setRoles(getRolesByUserId(user.getId(), connection));
                    return Optional.of(user);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user with login " + login, e);
        }
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (name, login, password) VALUES (?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                user.setId(resultSet.getLong(1));
                statement.close();
                addRolesToUser(user, connection);
                return user;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create user" + user.toString(), e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users WHERE user_id = ? AND deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = getUserFromResultSet(resultSet);
                    user.setRoles(getRolesByUserId(user.getId(), connection));
                    return Optional.of(user);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get use with id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users WHERE deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                user.setRoles(getRolesByUserId(user.getId(), connection));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all users", e);
        }
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users SET name = ?, login = ?, password = ? "
                + "WHERE user_id = ? AND deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setLong(5, user.getId());
            statement.executeUpdate();
            deleteUserFromRoles(user.getId(), connection);
            addRolesToUser(user, connection);
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update user " + user, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE users SET deleted = TRUE WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user with ID " + id, e);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("user_id");
        String name = resultSet.getString("name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        return new User(id, name, login, password);
    }

    private void addRolesToUser(User user, Connection connection) throws SQLException {
        String selectQuery = "SELECT role_id FROM roles WHERE role_name = ?";
        String addRolesQuery = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?);";
        try (PreparedStatement selectStatement
                     = connection.prepareStatement(selectQuery);
                 PreparedStatement insertStatement
                         = connection.prepareStatement(addRolesQuery)) {
            for (Role role : user.getRoles()) {
                selectStatement.setString(1, role.getRoleName().name());
                ResultSet resultSet = selectStatement.executeQuery();
                resultSet.next();
                insertStatement.setLong(1, user.getId());
                insertStatement.setLong(2, resultSet.getLong("role_id"));
                insertStatement.executeUpdate();
            }
        }
    }

    private Set<Role> getRolesByUserId(Long userId, Connection connection) throws SQLException {
        String query = "SELECT role_name FROM users_roles "
                + "JOIN roles USING (role_id) WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                Set<Role> roles = new HashSet<>();
                while (resultSet.next()) {
                    roles.add(Role.of(resultSet.getString("role_name")));
                }
                return roles;
            }
        }
    }

    private void deleteUserFromRoles(Long userId, Connection connection) throws SQLException {
        String query = "DELETE FROM users_roles WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.executeUpdate();
        }
    }
}
