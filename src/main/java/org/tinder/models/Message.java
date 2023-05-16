package org.tinder.models;

import org.tinder.interfaces.Model;


import java.sql.ResultSet;
import java.sql.SQLException;

public class Message implements Model {

    private final Integer id;
    private final Chat chat;
    private final User user;
    private String content;


    public Message(Integer id, Chat chat, User user, String content) {
        this.id = id;
        this.chat = chat;
        this.user = user;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Chat getChat() {
        return chat;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Integer id() {
        return this.id;
    }

    public static Message load(ResultSet rs){
        if (rs == null) return null;
        try {
            return new Message(
                    rs.getInt("message_id"),
                    new Chat(rs.getInt("chat_id")),
                    User.load(rs),
                    rs.getString("content")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
