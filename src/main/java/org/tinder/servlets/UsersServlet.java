package org.tinder.servlets;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.tinder.enums.CookieNames;
import org.tinder.models.Like;
import org.tinder.models.User;
import org.tinder.services.Services;
import org.tinder.utils.CookieWorker;
import org.tinder.utils.FMTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

public class UsersServlet extends ServicesServlet {

    public UsersServlet(Services services) {
        super(services);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        renderPage(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            services.likeService.addLike(Like.createUnsafe(
                        Integer.parseInt(req.getParameter("userFrom")),
                        Integer.parseInt(req.getParameter("userTo")),
                        req.getParameter("response").equals("Yes")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        renderPage(req, resp);
    }

    private void renderPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Object> data = new HashMap<>();
        String token = CookieWorker.getCookieOrThrow(req, CookieNames.AUTH_TOKEN);
        User currentUser = services.user.login(token);
        try {
            data.put("userFrom", currentUser.id());
            User oneForLike = services.user.getById(services.likeService.oneUserForLikes(currentUser.id()));
            data.put("userTo", oneForLike.id());
            data.put("avatar", oneForLike.avatar());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PrintWriter w = resp.getWriter()) {
            Template template = FMTemplate.getTemplate("users.ftl");
            template.process(data, w);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

}
