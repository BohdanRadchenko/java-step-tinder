package org.tinder.dao;

import org.tinder.interfaces.DAO;
import org.tinder.models.Like;

import java.sql.*;
import java.util.LinkedList;
import java.util.Optional;

public class LikeDao implements DAO<Like> {
    private final Connection conn;

    public LikeDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        throw new RuntimeException("Impossible by design");
    }

    @Override
    public Optional<Like> getById(Integer id) throws Exception {
        PreparedStatement ps = conn.prepareStatement(
                """
                    SELECT
                        like_id,
                        user_from,
                        user_to,
                        is_liked,
                        is_match
                    FROM likes
                    WHERE like_id = ?
                    """
        );
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return Optional.of(
                new Like(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getBoolean(4),
                        rs.getBoolean(5)
                )
        );
        else return Optional.empty();
    }

    @Override
    public boolean save(Like model) throws Exception {
        boolean res = insertNew(model);
        matchCheck(
                model.userFromId(),
                model.userToId()
        );
        return res;
    }

    private boolean insertNew(Like model) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                """
                    INSERT INTO likes
                        (user_from,
                         user_to,
                         is_liked,
                         is_match)
                     values
                         (?, ?, ?, ?)
                     """
        );
        ps.setInt(1, model.userFromId());
        ps.setInt(2, model.userToId());
        ps.setBoolean(3, model.isLiked());
        ps.setBoolean(4, model.isMatch());
        return ps.execute();
    }

    private void matchCheck(int id1, int id2) throws SQLException {
        Optional<Like> from = getOne(id1, id2);
        Optional<Like> to = getOne(id2, id1);
        if (from.isPresent() && to.isPresent()) {
            updateMatch(from.get().id());
            updateMatch(to.get().id());
        }
    }

    private Optional<Like> getOne(int idFrom, int idTo) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                """
                    SELECT
                        like_id,
                        user_from,
                        user_to,
                        is_liked,
                        is_match
                    FROM
                        likes
                    WHERE
                        user_from = ? and user_to = ? and is_liked = ?
                     """
        );
        ps.setInt(1, idFrom);
        ps.setInt(2, idTo);
        ps.setBoolean(3, true);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return Optional.of(
                new Like(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getBoolean(4),
                        rs.getBoolean(5)
                )
        );
        else return Optional.empty();
    }

    private void updateMatch(Integer id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                """
                    UPDATE likes
                    SET is_match = ?
                    WHERE like_id = ?
                    """
        );
        ps.setBoolean(1, true);
        ps.setInt(2, id);
        ps.execute();
    }

    public Integer getAnyUserForLikes(Integer currentUserId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                """
                    SELECT u.id
                    FROM users u
                    LEFT JOIN likes l ON u.id = l.user_to AND l.user_from = ?
                    WHERE l.user_from IS NULL AND u.id != ?
                    LIMIT 1
                    """
        );
        ps.setInt(1, currentUserId);
        ps.setInt(2, currentUserId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        }

        return null;
    }


    public LinkedList<Integer> getAllLikedUsers(Integer currentUserId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                """
                    SELECT user_to
                    FROM likes
                    WHERE user_from = ? AND is_liked = ?
                    """
        );
        ps.setInt(1, currentUserId);
        ps.setBoolean(2, true);
        ResultSet rs = ps.executeQuery();
        LinkedList<Integer> idList = new LinkedList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            idList.add(id);
        }
        return idList;
    }



}
