package org.tinder.dao;

import org.tinder.interfaces.DAO;
import org.tinder.models.User;
import org.tinder.utils.SqlRequest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao implements DAO<User> {
    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public int create(User user) throws SQLException {
        String sql = "INSERT INTO users (login, email, password) values (?, ?, ?)";
        return SqlRequest
                .of(connection, sql)
                .setString(
                        user.login(),
                        user.email(),
                        user.password()
                )
                .update();
    }


    public int update(User user) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public Optional<User> getById(Integer id) throws SQLException {
        String sql = "SELECT id, login, email, password, avatar FROM users WHERE id = ?";
        ResultSet rs = SqlRequest
                .of(connection, sql)
                .setInt(id)
                .query();
        if (!rs.next()) {
            return Optional.empty();
        } else {
            return Optional.of(User.load(rs));
        }
    }

    @Override
    public boolean save(User model) throws Exception {
        if (model.id() == null) {
            return create(model) >= 1;
        }
        return update(model) >= 1;

    }

    public Optional<User> getByEmail(String email) throws SQLException {
        String sql = "SELECT id, login, email, password, avatar FROM users WHERE email = ?";
        ResultSet rs = SqlRequest
                .of(connection, sql)
                .setString(email)
                .query();
        if (!rs.next()) {
            return Optional.empty();
        } else {
            return Optional.of(User.load(rs));
        }
    }

    public Optional<User> getByEmailOrLogin(String email, String login) throws SQLException {
        String sql = "SELECT id, login, email, password FROM users WHERE email = ? OR login = ? ";
        ResultSet rs = SqlRequest
                .of(connection, sql)
                .setString(email, login)
                .query();
        if (!rs.next()) {
            return Optional.empty();
        } else {
            return Optional.of(User.load(rs));
        }
    }

    public static UserDao of(Connection connection) {
        return new UserDao(connection);
    }
}
