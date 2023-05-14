package org.tinder.services;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.tinder.dao.UserDao;
import org.tinder.exceptions.DatabaseException;
import org.tinder.exceptions.NotFoundException;
import org.tinder.models.User;
import org.tinder.utils.Config;
import org.tinder.utils.Database;
import org.tinder.utils.Encryptor;
import org.tinder.utils.JWTToken;

import java.sql.SQLException;
import java.util.Optional;

public class UserService {
    private final UserDao db = new UserDao(Database.getConnection());

    public User getByUuId(String uuid) throws DatabaseException {
        try {
            Optional<User> user = db.getByUuId(uuid);
            if (user.isEmpty()) {
                throw new DatabaseException(String.format("User with uuid: %s not found", uuid));
            }
            return user.get();
        } catch (SQLException | DatabaseException ex) {
            throw new DatabaseException(ex);
        }
    }

    public User getFromToken(String token) throws DatabaseException, JWTVerificationException {
        DecodedJWT decode = JWTToken.verify(token, Config.getAccessTokenKey());
        String uuid = decode.getClaim("uuid").asString();
        return getByUuId(uuid);
    }

    public User login(String email, String password) throws NotFoundException, IllegalArgumentException, DatabaseException {
        Encryptor enc = Encryptor.of(Config.getPasswordHashKey());
        try {
            Optional<User> userOptional = db.getByEmail(email);
            if (userOptional.isEmpty()) {
                throw new NotFoundException("User not found!");
            }
            User user = userOptional.get();
            if (!enc.check(password, user.getPassword())) {
                throw new IllegalArgumentException("Invalid password");
            }
            return user;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }
}
