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
        data.put("emailParam", ServletParams.EMAIL.getParam());
        data.put("passwordParam", ServletParams.PASSWORD.getParam());
        data.put("registerLinkParam", ServletPath.REGISTER.getPath());

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
        String email = req.getParameter(ServletParams.EMAIL.getParam());
        String password = req.getParameter(ServletParams.PASSWORD.getParam());
        try {
            User user = services.user.login(email, password);
            JWTToken token = JWTToken.of(Config.getAccessTokenKey(), Constants.AUTH_EXPIRED_TIME);
            token.addClaim("uuid", user.getUuidString());
            token.addClaim("login", user.getLogin());
            token.addClaim("email", user.getEmail());
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
