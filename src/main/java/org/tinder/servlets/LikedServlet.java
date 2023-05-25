package org.tinder.servlets;

import freemarker.template.*;
import org.tinder.enums.CookieNames;
import org.tinder.enums.ServletPath;
import org.tinder.exceptions.DatabaseException;
import org.tinder.models.User;
import org.tinder.services.Services;
import org.tinder.utils.*;

import javax.servlet.http.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class LikedServlet extends ServicesServlet {
    public LikedServlet(Services services) {
        super(services);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, Object> data = new HashMap<>();
        try {
            String token = CookieWorker.getCookieOrThrow(req, CookieNames.AUTH_TOKEN);
            User currentUser = services.user.login(token);

            try {
                ArrayList<User> likedUsers = services.likeService.allLikedUsers(currentUser.id())
                        .stream()
                        .map(userId -> {
                            try {
                                return services.user.getById(userId);
                            } catch (DatabaseException ex) {
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toCollection(ArrayList::new));

                data.put("likedUsers", likedUsers);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            Responses.redirect(resp, ServletPath.LOGIN);
        }


        try (PrintWriter w = resp.getWriter()) {
//            Template template = FMTemplate.getTemplate("liked.ftl");
            Template template = FMTemplate.getTemplate("people-list.ftl");
            template.process(data, w);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
