package org.tinder.servlets;

import freemarker.template.*;
import org.tinder.enums.CookieNames;
import org.tinder.models.User;
import org.tinder.services.Services;
import org.tinder.utils.*;

import javax.servlet.http.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

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
        String token = CookieWorker.getCookieOrThrow(req, CookieNames.AUTH_TOKEN);
        User currentUser = services.user.login(token);

        try {
            LinkedList<Integer> likedUsers = services.likeService.allLikedUsers(currentUser.id());
            HashMap<String, User> users = new HashMap<>();
            for (Integer userId : likedUsers) {
                User user = services.user.getById(userId);
                users.put(userId.toString(), user);
            }
            data.put("likedUsers", likedUsers);
            data.put("users", users);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PrintWriter w = resp.getWriter()) {
            Template template = FMTemplate.getTemplate("liked.ftl");
            template.process(data, w);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
