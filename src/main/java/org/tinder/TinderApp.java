package org.tinder;

import org.tinder.enums.ServletPath;
import org.tinder.exceptions.DatabaseException;
import org.tinder.filters.AuthRequestFilter;
import org.tinder.filters.ProfileFilter;
import org.tinder.services.Services;
import org.tinder.servlets.*;
import org.tinder.utils.*;

import javax.servlet.MultipartConfigElement;

public class TinderApp {
    private static void init() {
        OsUtil.osInit();
    }

    public static void main(String[] args) {
        init();
        try {
            Database db = Database.of();
            db
                    .migrate()
                    .connect();

            HTTPServer server = new HTTPServer(Config.getPort());

            Services services = Services.create();

            // static content
            server.addServlet(new StaticServlet(ResourcesOps.dir(Constants.STATIC_CONTENT_DIR)), ServletPath.STATIC);

            // home
            server.addServlet(new HomeServlet(services), ServletPath.HOME, new AuthRequestFilter(services));

            // Auth
            server.addServlet(new LogoutServlet(), ServletPath.LOGOUT, new AuthRequestFilter(services));
            server.addServlet(new LoginServlet(services), ServletPath.LOGIN);
            server.addServlet(new RegisterServlet(services), ServletPath.REGISTER);

            // profile
            server
                    .addServlet(new ProfileServlet(services), ServletPath.PROFILE, new AuthRequestFilter(services), new ProfileFilter())
                    .getRegistration()
                    .setMultipartConfig(new MultipartConfigElement(
                            Constants.MULTIPART_CONFIG_LOCATION,
                            Constants.MAX_FILE_UPLOAD_SIZE,
                            Constants.MAX_REQUEST_SIZE,
                            Constants.FILE_SIZE_THRESHOLD
                    ));


            server.start();
        } catch (DatabaseException ex) {
            System.out.println("Database connection error...");
            System.out.println(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}