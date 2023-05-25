package org.tinder.services;

import org.tinder.dao.MessageDao;
import org.tinder.models.Chat;
import org.tinder.models.Message;
import org.tinder.models.User;
import org.tinder.utils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MessageService {
    private final MessageDao messageDao;

    public MessageService() {

        this.messageDao = new MessageDao(Database.getConnection());
    }


    public boolean saveMessage(String content, User user, Integer chatID){
        if (content == null) return false;
        if (content.length() == 0) return false;
        try {
            return messageDao.save(new Message(
                     null,
                     new Chat(chatID),
                     user,
                     content,
                     LocalDateTime.now()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Message> getMessagesByChatId(Integer chat_id) {
        ArrayList<Message> messages = new ArrayList<>();
        HashMap<Integer, User> userHashMap = new HashMap<>();

        try {
            ResultSet rs = messageDao.getMessagesByChatId(chat_id);
            while (rs.next()) {
                User user = getUserFromCache(userHashMap, rs);
                messages.add(new Message(
                        rs.getInt("message_id"),
                        new Chat(rs.getInt("chat_id")),
                        user,
                        rs.getString("content"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public Optional<Integer> createNewChat(Integer currentUsersId, Integer userId){
        try {
            return messageDao.getChatId(currentUsersId, userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
