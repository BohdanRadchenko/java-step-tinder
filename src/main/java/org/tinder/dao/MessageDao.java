package org.tinder.dao;


import org.tinder.interfaces.DAO;
import org.tinder.models.Chat;
import org.tinder.models.Message;
import org.tinder.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class MessageDao implements DAO<Message> {

    private final Connection conn;

    public MessageDao(Connection conn) {
        this.conn = conn;
    }

    public ResultSet getMessagesByChatId(Integer chatId) throws SQLException {
        PreparedStatement preparedStatement =
                                conn.prepareStatement(
                                        String.format(getTextQueryMessages(),
                                                "WHERE mess.chat_id = (?)"));

        preparedStatement.setInt(1,chatId);
        return preparedStatement.executeQuery();
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        throw new RuntimeException("Not implement");
    }

    @Override
    public Optional<Message> getById(Integer id) throws Exception {
        PreparedStatement preparedStatement =
                                conn.prepareStatement(
                                        String.format(getTextQueryMessages(),
                                                "WHERE message_id = (?)"));

        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()){
           return Optional.of(Message.load(rs));
        }else {
            return Optional.empty();
        }
    }

    private String getTextQueryMessages(){
        return """
                 SELECT mess.message_id,
                       mess.chat_id,
                       mess.user_id,
                       mess.content,
                       u.login,
                       u.password,
                       u.email,
                       ci.user_id as chat_user_id,
                       ci.name
                FROM messages mess
                    LEFT JOIN users u
                        ON mess.user_id = u.id
                    LEFT JOIN chat_id ci
                        ON ci.id = mess.chat_id
                %s
                """;
    }

    @Override
    public boolean save(Message message) throws Exception {
        throw new RuntimeException("Not implement");
    }



}
