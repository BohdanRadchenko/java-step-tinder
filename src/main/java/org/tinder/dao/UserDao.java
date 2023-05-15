package org.tinder.dao;

import org.tinder.models.User;
import org.tinder.utils.SqlRequest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao extends DAO<User> {

    public UserDao(Connection connection) {
        super(connection);
    }

    @Override
    public int create(User user) throws SQLException {
        String sql = "INSERT INTO users (uuid, login, email, password) values (?, ?, ?, ?)";
        return SqlRequest
                .of(connection, sql)
                .setString(
                        user.getUuidString(),
                        user.getLogin(),
                        user.getEmail(),
                        user.getPassword()
                )
                .update();
    }

    @Override
    public int update(User user) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public boolean delete(String id) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public Optional<User> getById(String id) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    public Optional<User> getByUuId(String uuid) throws SQLException {
        String sql = "SELECT id, uuid, login, email, password FROM users WHERE uuid = ?";
        ResultSet rs = SqlRequest
                .of(connection, sql)
                .setString(uuid)
                .query();
        if (!rs.next()) {
            return Optional.empty();
        } else {
            return Optional.of(User.load(rs));
        }
    }

    public Optional<User> getByEmail(String email) throws SQLException {
        String sql = "SELECT id, uuid, login, email, password FROM users WHERE email = ?";
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
        String sql = "SELECT id, uuid, login, email, password FROM users WHERE email = ? OR login = ? ";
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
}
