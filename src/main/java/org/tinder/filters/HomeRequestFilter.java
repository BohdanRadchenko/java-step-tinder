package org.tinder.filters;

import org.tinder.enums.ServletPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeRequestFilter extends RequestFilter {
    @Override
    boolean accept(HttpServletRequest req, HttpServletResponse res) {
        return true;
    }

    @Override
    void failed(HttpServletRequest req, HttpServletResponse res) {
        try {
            res.sendRedirect(ServletPath.HOME.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
