package org.tinder.utils;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.tinder.exceptions.DatabaseException;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;
    private static Flyway flyway;
    private final String url;
    private final String user;
    private final String password;

    public Database(String uri, String user, String password) {
        this.url = uri;
        this.user = user;
        this.password = password;
        FluentConfiguration conf = new FluentConfiguration()
                .cleanDisabled(false)
                .dataSource(uri, user, password);
        flyway = new Flyway(conf);
    }
    public Database(String uri) {
        try {
            URI dbUri = new URI(uri);
            this.user = dbUri.getUserInfo().split(":")[0];
            this.password = dbUri.getUserInfo().split(":")[1];
            this.url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
            FluentConfiguration conf = new FluentConfiguration()
                    .cleanDisabled(false)
                    .dataSource(url, user, password);
            flyway = new Flyway(conf);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Database() {
        this(Config.getDatabaseUrl());
//        this(
//                Config.getDbConnectionUri(),
//                Config.getDbUser(),
//                Config.getDbPassword()
//        );
    }

    public Database connect() throws DatabaseException {
        try {
            connection = DriverManager.getConnection(url, user, password);
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

    public Database baseline() throws DatabaseException {
        if (flyway == null) {
            throw new DatabaseException("Missing flyway instance");
        }
        try {
            flyway.baseline();
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
