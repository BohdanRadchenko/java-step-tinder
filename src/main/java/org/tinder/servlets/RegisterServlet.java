package org.tinder.servlets;

import freemarker.template.TemplateException;
import org.tinder.enums.ServletParams;
import org.tinder.enums.ServletPath;
import org.tinder.exceptions.AlreadyExistException;
import org.tinder.exceptions.DatabaseException;
import org.tinder.models.User;
import org.tinder.services.Services;
import org.tinder.utils.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class RegisterServlet extends ServicesServlet {
    public RegisterServlet(Services services) {
        super(services);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Object> data = new HashMap<>();
        data.put(ServletParams.LOGIN.name(), ServletParams.LOGIN.param());
        data.put(ServletParams.EMAIL.name(), ServletParams.EMAIL.param());
        data.put(ServletParams.PASSWORD.name(), ServletParams.PASSWORD.param());
        data.put(ServletParams.PASSWORD_CONFIRM.name(), ServletParams.PASSWORD_CONFIRM.param());
        data.put(ServletParams.LOGIN_LINK.name(), ServletParams.LOGIN_LINK.param());

        try (PrintWriter w = resp.getWriter()) {
            FMTemplate
                    .getTemplate("register.ftl")
                    .process(data, w);
        } catch (TemplateException x) {
            throw new RuntimeException(x);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(ServletParams.LOGIN.param());
        String email = req.getParameter(ServletParams.EMAIL.param());
        String password = req.getParameter(ServletParams.PASSWORD.param());

        try {
            User user = services.user.register(login, email, password);

            JWTToken token = JWTToken.makeAccess(user);

            CookieWorker.auth(resp, token.sign());

            System.out.printf("User created. ID: %s. LOGIN: %s", user.id(), user.login());
            Responses.redirect(resp, ServletPath.HOME);
        } catch (AlreadyExistException ex) {
            Responses.forbidden(resp, ex.getMessage());
        } catch (DatabaseException ex) {
            Responses.internalError(resp, ex.getMessage());
        }
    }
}
