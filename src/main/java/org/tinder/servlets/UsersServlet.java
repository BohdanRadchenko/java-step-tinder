package org.tinder.servlets;

import freemarker.template.*;
import org.tinder.enums.CookieNames;
import org.tinder.enums.ServletPath;
import org.tinder.models.*;
import org.tinder.services.Services;
import org.tinder.utils.*;

import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;

public class UsersServlet extends ServicesServlet {

    public UsersServlet(Services services) {
        super(services);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        renderPage(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            services.likeService.addLike(Like.createUnsafe(
                        Integer.parseInt(req.getParameter("userFrom")),
                        Integer.parseInt(req.getParameter("userTo")),
                        req.getParameter("response").equals("Yes")));
        } catch (Exception e) {
            Responses.redirect(resp, ServletPath.LIKED);
        }
        renderPage(req, resp);
    }

    private void renderPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, Object> data = new HashMap<>();
        String token = CookieWorker.getCookieOrThrow(req, CookieNames.AUTH_TOKEN);
        User currentUser = services.user.login(token);
        try {
            data.put("userFrom", currentUser.id());
            User oneForLike = services.user.getById(services.likeService.oneUserForLikes(currentUser.id()));
            data.put("userTo", oneForLike.id());
            data.put("avatar", oneForLike.avatar());
        } catch (Exception e) {
            Responses.redirect(resp, ServletPath.LIKED);
        }

        try (PrintWriter w = resp.getWriter()) {
            Template template = FMTemplate.getTemplate("users.ftl");
            template.process(data, w);
        } catch (TemplateException e) {
            Responses.redirect(resp, ServletPath.LIKED);
        }
    }

}
