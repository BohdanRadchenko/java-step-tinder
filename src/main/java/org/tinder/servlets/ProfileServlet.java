package org.tinder.servlets;

import freemarker.template.TemplateException;
import org.tinder.enums.CookieNames;
import org.tinder.enums.ServletParams;
import org.tinder.enums.ServletPath;
import org.tinder.models.User;
import org.tinder.services.Services;
import org.tinder.utils.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class ProfileServlet extends ServicesServlet {
    public ProfileServlet(Services services) {
        super(services);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Object> data = new HashMap<>();
        String token = CookieWorker.getCookieOrThrow(req, CookieNames.AUTH_TOKEN);
        data.put(ServletParams.CURRENT_USER.name(), User.load(token));

        try (PrintWriter w = resp.getWriter()) {
            FMTemplate
                    .getTemplate("profile.ftl")
                    .process(data, w);
        } catch (TemplateException x) {
            throw new RuntimeException(x);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getAttribute("currentUser");

        Part avatarPart = req.getPart("avatar");
        String id = UUID.randomUUID().toString();
        String[] split = avatarPart.getSubmittedFileName().split("\\.");
        String type = split[split.length - 1];
        String name = String.format("%s.%s", id, type);
        String pathAbsolute = Constants.STATIC_FILES_AVATARS + name;
        String pathFileStatic = pathAbsolute.replaceAll(Constants.DIR_PATH, "");
        System.out.println("pathAbsolute " + pathAbsolute);
        System.out.println("pathFileStatic " + pathFileStatic);
        avatarPart.write(pathAbsolute);

        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String profession = req.getParameter("profession");

        User updatedUser = services.user.updateProfile(User.load(
                user.id(),
                user.login(),
                user.email(),
                user.password(),
                firstName,
                lastName,
                profession,
                pathFileStatic));

        String token = JWTToken.makeAccess(updatedUser).sign();

        CookieWorker.auth(resp, token);
        Responses.redirect(resp, ServletPath.HOME);
    }
}
