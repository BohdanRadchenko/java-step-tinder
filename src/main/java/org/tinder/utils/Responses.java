package org.tinder.utils;

import com.google.gson.Gson;
import org.tinder.enums.ServletPath;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class Responses {
    private static final Gson gson = new Gson();

    public static void messageJson(HttpServletResponse res, String message) throws IOException {
        if (message == null) return;
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().println(gson.toJson(message));
    }

    public static void message(HttpServletResponse res, String message) throws IOException {
        if (message == null) return;
        try (Writer w = res.getWriter()) {
            w.write(message);
        }
    }

    public static void badRequest(HttpServletResponse res, String message) throws IOException {
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        messageJson(res, message);
    }

    public static void badRequest(HttpServletResponse res) throws IOException {
        badRequest(res, null);
    }

    public static void notFound(HttpServletResponse res, String message) throws IOException {
        res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        messageJson(res, message);
    }

    public static void forbidden(HttpServletResponse res, String message) throws IOException {
        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        messageJson(res, message);
    }

    public static void internalError(HttpServletResponse res, String message) throws IOException {
        res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        messageJson(res, message);
    }

    public static void internalError(HttpServletResponse res) throws IOException {
        internalError(res, "Oops... Something wrong!");
    }

    public static void redirect(HttpServletResponse res, ServletPath to) throws IOException {
        res.sendRedirect(to.path());
    }
}
