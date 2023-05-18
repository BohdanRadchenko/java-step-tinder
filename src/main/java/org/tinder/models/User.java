package org.tinder.models;


import org.tinder.interfaces.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public record User(Integer id, String login, String email, String password) implements Model {
    public static User create(String login, String email, String password) {
        return new User(null, login, email, password);
    }

    public static User load(Integer id, String login, String email, String password) {
        return new User(id, login, email, password);
    }

    public static User load(ResultSet resultSet) {
        if (resultSet == null) return null;
        try {
            int id = resultSet.getInt("user_id");
            String login = resultSet.getString("login");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            return new User(id, login, email, password);
        } catch (SQLException ignored) {
            return null;
        }
    }
}
