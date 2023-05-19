package org.tinder.servlets;

import com.auth0.jwt.interfaces.DecodedJWT;
import freemarker.template.TemplateException;
import org.tinder.enums.CookieNames;
import org.tinder.enums.ServletParams;
import org.tinder.models.User;
import org.tinder.services.Services;
import org.tinder.utils.Config;
import org.tinder.utils.CookieWorker;
import org.tinder.utils.FMTemplate;
import org.tinder.utils.JWTToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class HomeServlet extends ServicesServlet {
    public HomeServlet(Services services) {
        super(services);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Object> data = new HashMap<>();
        String token = CookieWorker.getCookieOrThrow(req, CookieNames.AUTH_TOKEN);
        data.put(ServletParams.CURRENT_USER.name(), User.load(token));

        try (PrintWriter w = resp.getWriter()) {
            FMTemplate
                    .getTemplate("test.ftl")
                    .process(data, w);
        } catch (TemplateException x) {
            throw new RuntimeException(x);
        }
    }
}
