package org.tinder.utils;

public class SqlString {
    public static final String USER_FIELDS_FULL = "id as user_id, login, email, password, first_name, last_name, profession, avatar";

    public static String userSelectFullById() {
        return String.format("SELECT %s FROM users WHERE id = ?", USER_FIELDS_FULL);
    }
    public static String userSelectFullByEmail() {
        return String.format("SELECT %s FROM users WHERE email = ?", USER_FIELDS_FULL);
    }

    public static String userSelectFullByLoginOrEmail() {
        return String.format("SELECT %s FROM users WHERE email = ? OR login = ?", USER_FIELDS_FULL);
    }

    public static String userInsertCreate() {
        return "INSERT INTO users (login, email, password) values (?, ?, ?)";
    }

    public static String userUpdateFull() {
        return """
                UPDATE users
                SET login = ?,
                    email = ?,
                    password = ?,
                    first_name = ?,
                    last_name = ?,
                    profession = ?,
                    avatar =?
                WHERE id = ?
                """;
    }

    public static String userUpdateProfile() {
        return """
                UPDATE users
                SET first_name = ?,
                    last_name = ?,
                    profession = ?,
                    avatar =?
                WHERE id = ?
                """;
    }
}
