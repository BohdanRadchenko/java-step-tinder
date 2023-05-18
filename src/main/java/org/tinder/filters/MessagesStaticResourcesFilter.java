package org.tinder.filters;

import org.tinder.services.Services;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MessagesStaticResourcesFilter extends RequestFilter{

    public MessagesStaticResourcesFilter(Services services) {
        super(services);
    }
    @Override
    boolean accept(HttpServletRequest req, HttpServletResponse res) {

        String path = req.getRequestURI().substring(req.getContextPath().length());
        return !path.startsWith("/messages/static/");
    }

    @Override
    void failed(HttpServletRequest req, HttpServletResponse res) {
        String newPath = req.getRequestURI()
                .substring(req.getContextPath().length())
                .replace("/messages/static/", "/static/");
        RequestDispatcher dispatcher = req.getRequestDispatcher(newPath);
        try {
            dispatcher.forward(req, res);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }


    }
}
