package org.tinder.models;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class User extends Model {
    private final String uuid;
    private final String login;
    private final String email;
    private final String password;

    private User(Integer id, String uuid, String login, String email, String password) {
        super(id);
        this.uuid = uuid;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public static User create(String login, String email, String password) {
        return new User(null, UUID.randomUUID().toString(), login, email, password);
    }

    public static User load(ResultSet resultSet) {
        if (resultSet == null) return null;
        try {
            int id = resultSet.getInt("id");
            String uuid = resultSet.getString("uuid");
            String login = resultSet.getString("login");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            return new User(id, uuid, login, email, password);
        } catch (SQLException ignored) {
            return null;
        }
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public String getUuidString() {
        return uuid;
    }
}
