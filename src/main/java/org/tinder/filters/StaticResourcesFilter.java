package org.tinder.filters;

import org.tinder.services.Services;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StaticResourcesFilter extends RequestFilter{

    public StaticResourcesFilter(Services services) {
        super(services);
    }
    @Override
    boolean accept(HttpServletRequest req, HttpServletResponse res) {

        return !(req.getPathInfo().startsWith("/static") && !req.getRequestURI().startsWith("/static/"));
    }

    @Override
    void failed(HttpServletRequest req, HttpServletResponse res) {
        String newPath = req.getRequestURI()
                .substring(req.getRequestURI().length()- req.getPathInfo().length());

        RequestDispatcher dispatcher = req.getRequestDispatcher(newPath);
        try {
            dispatcher.forward(req, res);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }


    }
}
