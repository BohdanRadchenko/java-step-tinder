package org.tinder.filters;

import org.tinder.enums.ServletPath;
import org.tinder.utils.Responses;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileFilter extends RequestFilter {

    @Override
    boolean accept(HttpServletRequest req, HttpServletResponse res) {
        if(req.getMethod().equals("GET")) return true;
        try {
            String firstName = req.getParameter("first_name");
            String lastName = req.getParameter("last_name");
            String profession = req.getParameter("profession");

            if (
                    firstName == null || firstName.isBlank()
                    && lastName == null || lastName.isBlank()
                    && profession == null || profession.isBlank()
            ) return false;

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    void failed(HttpServletRequest req, HttpServletResponse res) {
        try {
            Responses.redirect(res, ServletPath.HOME);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
