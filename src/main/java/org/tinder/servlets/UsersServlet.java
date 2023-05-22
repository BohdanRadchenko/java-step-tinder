package org.tinder.servlets;

import freemarker.template.TemplateException;
import org.tinder.enums.CookieNames;
import org.tinder.enums.ServletParams;
import org.tinder.models.User;
import org.tinder.services.Services;
import org.tinder.utils.CookieWorker;
import org.tinder.utils.FMTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

public class UsersServlet extends ServicesServlet {

    public UsersServlet(Services services) {
        super(services);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Object> data = new HashMap<>();
        String token = CookieWorker.getCookieOrThrow(req, CookieNames.AUTH_TOKEN);
        User currentUser = services.user.login(token);
        try {
            User oneForLike = services.user.getById(services.likeService.oneUserForLikes(currentUser.id()));
            data.put("newUserToShow", oneForLike);
//            ????
//            ????
//            ????
//            ????
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PrintWriter w = resp.getWriter()) {
            FMTemplate
                    .getTemplate("people-list.ftl")
                    .process(data, w);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }
}
