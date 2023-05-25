package org.tinder.dao;

import org.tinder.interfaces.DAO;
import org.tinder.models.User;
import org.tinder.utils.SqlRequest;
import org.tinder.utils.SqlString;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserDao implements DAO<User> {
    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public int create(User user) throws SQLException {
        return SqlRequest
                .of(connection, SqlString.userInsertCreate())
                .setString(
                        user.login(),
                        user.email(),
                        user.password()
                )
                .update();
    }

    public int update(User user) throws SQLException {
        return SqlRequest
                .of(connection, SqlString.userUpdateFull())
                .setString(
                        user.login(),
                        user.email(),
                        user.password(),
                        user.firstName(),
                        user.lastName(),
                        user.profession(),
                        user.avatar()
                )
                .setInt(user.id())
                .update();
    }

    public int updateProfile(User user) throws SQLException {
        return SqlRequest
                .of(connection, SqlString.userUpdateProfile())
                .setString(
                        user.firstName(),
                        user.lastName(),
                        user.profession(),
                        user.avatar()
                )
                .setInt(user.id())
                .update();
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public Optional<User> getById(Integer id) throws SQLException {
        ResultSet rs = SqlRequest
                .of(connection, SqlString.userSelectFullById())
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
        ResultSet rs = SqlRequest
                .of(connection, SqlString.userSelectFullByEmail())
                .setString(email)
                .query();
        if (!rs.next()) {
            return Optional.empty();
        } else {
            return Optional.of(User.load(rs));
        }
    }

    public Optional<User> getByEmailOrLogin(String email, String login) throws SQLException {
        ResultSet rs = SqlRequest
                .of(connection, SqlString.userSelectFullByLoginOrEmail())
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




    public void insertUserLoginHistory(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user_login_history (user_id, login_time) values(?,?)");
        preparedStatement.setObject(1, user.id());
        preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
        preparedStatement.execute();
    }

}
