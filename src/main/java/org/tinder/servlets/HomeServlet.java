package org.tinder.servlets;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.tinder.enums.CookieNames;
import org.tinder.utils.Config;
import org.tinder.utils.CookieWorker;
import org.tinder.utils.JWTToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     try {
         String token = CookieWorker.getCookieOrThrow(req, CookieNames.AUTH_TOKEN);
         DecodedJWT verify = JWTToken.verify(token, Config.getAccessTokenKey());
         int id = verify.getClaim("id").asInt();
         String login = verify.getClaim("login").asString();
         String email = verify.getClaim("email").asString();
         try (PrintWriter w = resp.getWriter()) {
             w.println(String.format("ID: %s", id));
             w.println(String.format("LOGIN: %s", login));
             w.println(String.format("EMAIL: %s", email));
             w.write("Home page");
         }
     } catch (Exception ignored) {
         try (PrintWriter w = resp.getWriter()) {
             w.write("Home page without auth token");
         }
     }
    }
}
