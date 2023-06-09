package org.tinder.servlets;

import org.tinder.enums.ServletPath;
import org.tinder.utils.CookieWorker;
import org.tinder.utils.Responses;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CookieWorker.logout(resp);
        Responses.redirect(resp, ServletPath.LOGIN);
    }
}
