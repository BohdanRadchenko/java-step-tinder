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
    private final UserDao db = UserDao.of(Database.getConnection());

    public User getById(Integer id) throws DatabaseException {
        try {
            Optional<User> user = db.getById(id);
            if (user.isEmpty()) {
                throw new DatabaseException(String.format("User with id: %s not found", id));
            }
            return user.get();
        } catch (SQLException | DatabaseException ex) {
            throw new DatabaseException(ex);
        }
    }

    public User updateProfile (User user) throws DatabaseException {
        try {
            if(db.updateProfile(user) < 1) {
                throw new DatabaseException("The user has not been updated!");
            }
            return user;
        } catch (SQLException | DatabaseException ex) {
            throw new DatabaseException(ex);
        }
    }

    public User login(String token) throws NotFoundException, IllegalArgumentException {
        try {
            DecodedJWT decode = JWTToken.verify(token, Config.getAccessTokenKey());
            int id = decode.getClaim("id").asInt();
            String email = decode.getClaim("email").asString();
            String password = decode.getClaim("password").asString();
            User user = getById(id);
            boolean equalsEmail = user.email().equals(email);
            boolean equalsPassword = user.password().equals(password);
            if(!equalsEmail || !equalsPassword) {
                throw new IllegalArgumentException("Invalid login data");
            }
            return user;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Token has already expired", ex);
        }
    }

    public User login(String email, String password) throws NotFoundException, IllegalArgumentException, DatabaseException {
        Encryptor enc = Encryptor.of(Config.getPasswordHashKey());
        try {
            Optional<User> userOptional = db.getByEmail(email);
            if (userOptional.isEmpty()) {
                throw new NotFoundException("User not found!");
            }
            User user = userOptional.get();
            if (!enc.check(password, user.password())) {
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

    public void insertUserLoginHistory(User user){
        try {
            db.insertUserLoginHistory(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
