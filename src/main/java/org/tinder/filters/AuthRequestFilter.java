package org.tinder.filters;

import org.tinder.enums.CookieNames;
import org.tinder.enums.ServletPath;
import org.tinder.models.User;
import org.tinder.services.Services;
import org.tinder.utils.CookieWorker;
import org.tinder.utils.Responses;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthRequestFilter extends RequestFilter {

    public AuthRequestFilter(Services services) {
        super(services);
    }

    @Override
    boolean accept(HttpServletRequest req, HttpServletResponse res) {
        try {
            String token = CookieWorker.getCookieOrThrow(req, CookieNames.AUTH_TOKEN);
            User u = services.user.login(token);
            req.setAttribute("userId", u.id());
            req.setAttribute("currentUser", u);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    void failed(HttpServletRequest req, HttpServletResponse res) {
        try {
            CookieWorker.logout(res);
            Responses.redirect(res, ServletPath.LOGIN);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
