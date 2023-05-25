package org.tinder.dao;


import org.tinder.interfaces.DAO;
import org.tinder.models.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MessageDao implements DAO<Message> {

    private final Connection conn;

    public MessageDao(Connection conn) {
        this.conn = conn;
    }

    public ResultSet getMessagesByChatId(Integer chatId) throws SQLException {
        PreparedStatement preparedStatement =
                                conn.prepareStatement(
                                        String.format(getTextQueryMessages(),
                                                """
                                                    WHERE mess.chat_id = (?)
                                                ORDER BY mess.message_id
                                                """));

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
                       mess.created_at,
                       u.login,
                       u.password,
                       u.email,
                       u.first_name,
                       u.last_name,
                       u.profession,
                       u.avatar,
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
        PreparedStatement preparedStatement = conn.prepareStatement("""
                INSERT INTO
                    messages (chat_id, user_id, content)
                    values (?,?,?)
                """);
        preparedStatement.setInt(1, message.chat().id());
        preparedStatement.setInt(2, message.user().id());
        preparedStatement.setString(3, message.content());
        return preparedStatement.execute();
    }

    public Optional<Integer>  getChatId(Integer currentUsersId, Integer userId) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("""
                SELECT                    
                    cu.chat_id,
                    cu.user_id,
                    cu2.user_id
                FROM chat_user cu
                    JOIN chat_user cu2
                    on cu.chat_id=cu2.chat_id                    
                WHERE cu.user_id = (?) and cu2.user_id = (?)
                """);
        preparedStatement.setInt(1, currentUsersId);
        preparedStatement.setInt(2, userId);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()){
            return Optional.of(rs.getInt("chat_id"));
        }else {
            return createNewChat(currentUsersId, userId);
        }
    }

    private Optional<Integer> createNewChat(Integer currentUsersId, Integer userId) throws SQLException {
        conn.setAutoCommit(false);
        PreparedStatement ps1 = conn.prepareStatement("""
                INSERT INTO
                    chat_id (user_id, name)
                (SELECT
                    u.id,
                    u.login
                FROM users u
                WHERE u.id = ?)
                """);
        ps1.setInt(1, currentUsersId);
        ps1.execute();

        PreparedStatement ps2 = conn.prepareStatement("INSERT INTO chat_user (chat_id, user_id) (SELECT max(ci.id),(?)  FROM chat_id ci)");
        ps2.setInt(1, currentUsersId);
        ps2.execute();

        PreparedStatement ps3 = conn.prepareStatement("INSERT INTO chat_user (chat_id, user_id) (SELECT max(ci.id),(?)  FROM chat_id ci)");
        ps2.setInt(1, userId);
        ps2.execute();

        conn.commit();

        return getChatId(currentUsersId, userId);


    }



}
