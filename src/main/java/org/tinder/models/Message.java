package org.tinder.models;

import org.tinder.interfaces.Model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record Message(Integer id, Chat chat, User user, String content, LocalDateTime createdAt) implements Model {

    public static Message load(ResultSet rs){
        if (rs == null) return null;
        try {
            return new Message(
                    rs.getInt("message_id"),
                    new Chat(rs.getInt("chat_id")),
                    User.load(rs),
                    rs.getString("content"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCreatedMessage(){

        return this.createdAt.format(DateTimeFormatter.ofPattern("MMM d, h:mm a", Locale.ENGLISH));

    }
}
