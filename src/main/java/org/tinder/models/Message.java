package org.tinder.models;

import org.tinder.interfaces.Model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public record Message(Integer id, Chat chat, User user, String content, LocalDateTime at) implements Model {
    public static Message load(ResultSet rs){
        if (rs == null) return null;
        try {
            return new Message(
                    rs.getInt("message_id"),
                    new Chat(rs.getInt("chat_id")),
                    User.load(rs),
                    rs.getString("content"),
                    LocalDateTime.now()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
