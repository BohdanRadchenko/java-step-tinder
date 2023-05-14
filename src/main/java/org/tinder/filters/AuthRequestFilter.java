package org.tinder.filters;

import org.tinder.enums.CookieNames;
import org.tinder.enums.ServletPath;
import org.tinder.utils.Config;
import org.tinder.utils.CookieWorker;
import org.tinder.utils.JWTToken;
import org.tinder.utils.Responses;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthRequestFilter extends RequestFilter {
    @Override
    boolean accept(HttpServletRequest req, HttpServletResponse res) {
        try {
            String token = CookieWorker.getCookieOrThrow(req, CookieNames.AUTH_TOKEN);
            JWTToken.verify(token, Config.getAccessTokenKey());
            return true;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    @Override
    void failed(HttpServletRequest req, HttpServletResponse res) {
        try {
            Responses.redirect(res, ServletPath.LOGIN);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
