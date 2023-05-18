package org.tinder.models;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.tinder.interfaces.Model;
import org.tinder.utils.Config;
import org.tinder.utils.JWTToken;

import java.sql.ResultSet;
import java.sql.SQLException;

public record User(Integer id, String login, String email, String password, String avatar) implements Model {
    public static User create(String login, String email, String password) {
        return new User(null, login, email, password, null);
    }

    public static User load(Integer id, String login, String email, String password, String avatar) {
        return new User(id, login, email, password, avatar);
    }

    public static User load(ResultSet resultSet) {
        if (resultSet == null) return null;
        try {
            int id = resultSet.getInt("id");
            String login = resultSet.getString("login");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String avatar = resultSet.getString("avatar");
            return new User(id, login, email, password, avatar);
        } catch (SQLException ignored) {
            return null;
        }
    }

    public static User load(String token) {
        try {
            if(token == null) {
                throw new RuntimeException("Token is missing.");
            }

            DecodedJWT verify = JWTToken.verify(token, Config.getAccessTokenKey());
            int id = verify.getClaim("id").asInt();
            String login = verify.getClaim("login").asString();
            String email = verify.getClaim("email").asString();
            String passowrd = verify.getClaim("passowrd").asString();
            String avatar = verify.getClaim("avatar").asString();

            return new User(id, login, email, passowrd, avatar);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Invalid user token.", ex);
        }
    }
}
