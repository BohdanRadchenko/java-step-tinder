package org.tinder.models;


import com.auth0.jwt.interfaces.DecodedJWT;
import org.tinder.interfaces.Model;
import org.tinder.utils.Config;
import org.tinder.utils.JWTToken;

import java.sql.ResultSet;
import java.sql.SQLException;

public record User(Integer id, String login, String email, String password, String firstName, String lastName, String profession, String avatar) implements Model {
    public static User create(String login, String email, String password) {
        return new User(null, login, email, password, null, null, null, "static/img/avatar.jpg");
    }

    public static User load(Integer id, String login, String email, String password, String firstName, String lastName, String profession, String avatar) {
        return new User(id, login, email, password, firstName, lastName, profession, avatar);
    }

    public static User load(ResultSet resultSet) {
        try {
            if (resultSet == null) {
                throw new RuntimeException("Result set is missing");
            }
            int id = resultSet.getInt("id");
            String login = resultSet.getString("login");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String profession = resultSet.getString("profession");
            String avatar = resultSet.getString("avatar");
            return new User(id, login, email, password, firstName, lastName, profession, avatar);
        } catch (SQLException | RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
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
            String password = verify.getClaim("password").asString();
            String firstName = verify.getClaim("firstName").asString();
            String lastName = verify.getClaim("firstName").asString();
            String profession = verify.getClaim("profession").asString();
            String avatar = verify.getClaim("avatar").asString();

            return new User(id, login, email, password, firstName, lastName, profession, avatar);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Invalid user token.", ex);
        }
    }
}
