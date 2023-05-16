package org.tinder.services;

import org.tinder.dao.MessageDao;
import org.tinder.models.Chat;
import org.tinder.models.Message;
import org.tinder.models.User;
import org.tinder.utils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageService {
    private final MessageDao messageDao;

    public MessageService() throws SQLException {

        this.messageDao = new MessageDao(Database.getConnection());
    }

    public List<Message> getMessagesByChatId(Integer chat_id) throws SQLException {
        ArrayList<Message> messages = new ArrayList<>();
        HashMap<Integer, User> userHashMap = new HashMap<>();

        ResultSet rs = messageDao.getMessagesByChatId(chat_id);
        while (rs.next()){
            User user = getUserFromCache(userHashMap, rs);
            messages.add(new Message(
                    rs.getInt("message_id"),
                    new Chat(rs.getInt(chat_id)),
                    user,
                    rs.getString("content")
            ));
        }
        return messages;
    }

    private User getUserFromCache(HashMap<Integer, User> userHashMap, ResultSet rs) throws SQLException {
        Integer userId = rs.getInt("user_id");
        User user = userHashMap.get(userId);
        if (user == null){
            user = User.load(rs);
            userHashMap.put(userId, user);
        }
        return user;

    }

}
