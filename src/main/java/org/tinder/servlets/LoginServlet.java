package org.tinder.servlets;

import freemarker.template.TemplateException;
import org.tinder.enums.ServletParams;
import org.tinder.enums.ServletPath;
import org.tinder.exceptions.DatabaseException;
import org.tinder.exceptions.NotFoundException;
import org.tinder.models.User;
import org.tinder.services.Services;
import org.tinder.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class LoginServlet extends ServicesServlet {
    public LoginServlet(Services services) {
        super(services);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, Object> data = new HashMap<>();
        data.put(ServletParams.EMAIL.name(), ServletParams.EMAIL.param());
        data.put(ServletParams.PASSWORD.name(), ServletParams.PASSWORD.param());
        data.put(ServletParams.REGISTER_LINK.name(), ServletParams.REGISTER_LINK.param());

        try (PrintWriter w = resp.getWriter()) {
            FMTemplate
                    .getTemplate("login.ftl")
                    .process(data, w);
        } catch (TemplateException x) {
            throw new RuntimeException(x);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter(ServletParams.EMAIL.param());
        String password = req.getParameter(ServletParams.PASSWORD.param());
        try {
            User user = services.user.login(email, password);
            services.user.insertUserLoginHistory(user);

            JWTToken token = JWTToken.makeAccess(user);

            CookieWorker.auth(resp, token.sign());
            Responses.redirect(resp, ServletPath.HOME);
        } catch (NotFoundException ex) {
            Responses.notFound(resp, ex.getMessage());
        } catch (IllegalArgumentException ex) {
            Responses.badRequest(resp, ex.getMessage());
        } catch (DatabaseException ex) {
            Responses.internalError(resp, ex.getMessage());
        }
    }
}
