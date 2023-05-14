package org.tinder.utils;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.tinder.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;
    private static Flyway flyway;
    private final String uri;
    private final String user;
    private final String password;

    public Database(String uri, String user, String password) {
        this.uri = uri;
        this.user = user;
        this.password = password;
        FluentConfiguration conf = new FluentConfiguration()
                .cleanDisabled(false)
                .dataSource(uri, user, password);
        flyway = new Flyway(conf);
    }

    public Database() {
        this(
                Config.getDbConnectionUri(),
                Config.getDbUser(),
                Config.getDbPassword()
        );
    }

    public Database connect() throws DatabaseException {
        try {
            connection = DriverManager.getConnection(uri, user, password);
            if (connection.isClosed()) {
                throw new DatabaseException("Connection is closed!");
            }
            System.out.println("db connected...");
            return this;
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    public Database clean() throws DatabaseException {
        if (flyway == null) {
            throw new DatabaseException("Missing flyway instance");
        }
        try {
            flyway.clean();
        } catch (FlywayException ex) {
            throw new DatabaseException(ex);
        }
        return this;
    }

    public Database migrate() throws DatabaseException {
        if (flyway == null) {
            throw new DatabaseException("Missing flyway instance");
        }
        try {
            flyway.baseline();
            flyway.migrate();
            System.out.println("db migrated...");
        } catch (FlywayException ex) {
            throw new DatabaseException(ex);
        }
        return this;
    }

    public static Database of() {
        return new Database();
    }

    public static Connection getConnection() {
        if (connection == null) {
            throw new DatabaseException("Missing connection!");
        }
        return connection;
    }
}
