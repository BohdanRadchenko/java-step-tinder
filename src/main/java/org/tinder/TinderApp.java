package org.tinder;

import org.tinder.enums.ServletPath;
import org.tinder.exceptions.DatabaseException;
import org.tinder.filters.AuthRequestFilter;
import org.tinder.services.Services;
import org.tinder.servlets.*;
import org.tinder.utils.Config;
import org.tinder.utils.Database;
import org.tinder.utils.ResourcesOps;

public class TinderApp {
    public static void main(String[] args) {
        try {
            Database db = Database.of();
            db
                    .migrate()
                    .connect();

            HTTPServer server = new HTTPServer(Config.getPort());

            Services services = Services.create();

            // static content
            server.addServlet(new StaticServlet(ResourcesOps.dir("static")), ServletPath.STATIC);

            // home
            server.addServlet(new HomeServlet(), ServletPath.HOME);

            // Auth
            server.addServlet(new LogoutServlet(), ServletPath.LOGOUT, new AuthRequestFilter(services));
            server.addServlet(new LoginServlet(services), ServletPath.LOGIN);
            server.addServlet(new RegisterServlet(services), ServletPath.REGISTER);



            server.start();
        } catch (DatabaseException ex) {
            System.out.println("Database connection error...");
            System.out.println(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}