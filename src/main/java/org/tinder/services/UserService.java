package org.tinder.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.tinder.dao.UserDao;
import org.tinder.exceptions.AlreadyExistException;
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

    public User login(String token) throws NotFoundException, IllegalArgumentException {
        DecodedJWT decode = JWTToken.verify(token, Config.getAccessTokenKey());
        String uuid = decode.getClaim("uuid").asString();
        String email = decode.getClaim("email").asString();
        String password = decode.getClaim("password").asString();
        User user = getByUuId(uuid);
        boolean equalsEmail = user.getEmail().equals(email);
        boolean equalsPassword = user.getPassword().equals(password);
        if(!equalsEmail || !equalsPassword) {
            throw new IllegalArgumentException("Invalid login data");
        }
        return user;
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

    public User register(String login, String email, String password) throws DatabaseException, AlreadyExistException {
        Encryptor encryptor = new Encryptor(Config.getPasswordHashKey());
        String hashedPassword = encryptor.hash(password);
        User user = User.create(login, email, hashedPassword);

        try {
            Optional<User> userIn = db.getByEmailOrLogin(email, login);

            if (userIn.isPresent()) {
                throw new AlreadyExistException("User already exist!");
            }

            if (db.create(user) < 1) throw new DatabaseException("Oops... Something wrong!");
            return user;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }
}
