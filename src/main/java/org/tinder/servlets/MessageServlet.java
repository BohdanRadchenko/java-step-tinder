package org.tinder.servlets;

import freemarker.template.TemplateException;
import org.tinder.enums.CookieNames;
import org.tinder.models.Message;
import org.tinder.models.User;
import org.tinder.services.MessageService;
import org.tinder.services.Services;
import org.tinder.services.UserService;
import org.tinder.utils.CookieWorker;
import org.tinder.utils.FMTemplate;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

public class MessageServlet extends ServicesServlet{

    public MessageServlet(Services services) {
        super(services);

    }

    // http://localhost:8080/messages
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Integer id = getMessageId(req);
        String token = CookieWorker.getCookieOrThrow(req, CookieNames.AUTH_TOKEN);
        User user = services.user.login(token);

        HashMap<String, Object> dataModel = new HashMap<>();

        List<Message> messages = services.messageService.getMessagesByChatId(id);
        dataModel.put("messages", messages);
        dataModel.put("chat_name", id);
        dataModel.put("currentUser", user.id());


        try (PrintWriter printWriter = resp.getWriter()){
            FMTemplate
                    .getTemplate("chat.ftl")
                    .process(dataModel, printWriter);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getMessageId(req);
        String token = CookieWorker.getCookieOrThrow(req, CookieNames.AUTH_TOKEN);
        User user = services.user.login(token);
        String message = req.getParameter("message");
        services.messageService.saveMessage(message, user,id);
        resp.sendRedirect(String.format("/messages/%s", id));
    }

    private Integer getMessageId(HttpServletRequest req){
        if (req == null) return 0;

        String pathInfo = req.getPathInfo();

        if (pathInfo == null) return 0;

        if (pathInfo.startsWith("/")){
            String idString = pathInfo.substring(1);
            try {
                return Integer.parseInt(idString);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

}
