package org.tinder.services;

import org.tinder.dao.LikeDao;
import org.tinder.models.Like;
import org.tinder.utils.Database;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Optional;

public class LikeService {
    private final LikeDao likeDao;

    public LikeService() {
        this.likeDao = new LikeDao(Database.getConnection());
    }

    public Optional<Like> getOne(int userId) throws Exception {
        return likeDao.getById(userId);
    }

    public Integer oneUserForLikes(Integer currentUserId) throws SQLException {
        return likeDao.getAnyUserForLikes(currentUserId);
    }

    public LinkedList<Integer> allLikedUsers(Integer currentUserId) throws SQLException {
        return likeDao.getAllLikedUsers(currentUserId);
    }

    public void addLike(Like like) throws Exception {
        likeDao.save(like);
    }

}
