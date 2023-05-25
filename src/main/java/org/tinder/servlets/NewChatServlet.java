package org.tinder.servlets;

import org.tinder.enums.CookieNames;
import org.tinder.models.User;
import org.tinder.services.Services;
import org.tinder.utils.CookieWorker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class NewChatServlet extends ServicesServlet{

    public NewChatServlet(Services services) {
        super(services);

    }

    // http://localhost:8080/new_chat?userID=3
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String userID = req.getParameter("userID");
        String token = CookieWorker.getCookieOrThrow(req, CookieNames.AUTH_TOKEN);
        User currentUser = services.user.login(token);

        Optional<Integer> newChatId = services.messageService.createNewChat(currentUser.id(), Integer.parseInt(userID));

        resp.sendRedirect(String.format("/messages/%s",newChatId.get()));
    }


}
