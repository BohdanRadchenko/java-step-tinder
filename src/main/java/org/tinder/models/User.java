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

    public UUID getUuid() {
        return UUID.fromString(uuid);
    }

    public String getUuidString() {
        return uuid;
    }
}
