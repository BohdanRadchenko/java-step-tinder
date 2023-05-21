package org.tinder.models;

import org.tinder.interfaces.Model;


public record Like(Integer likeId, int userFromId, int userToId, boolean isLiked, boolean isMatch) implements Model {
    public Like createUnsafe(int userFromId, int userToId, boolean isLiked) {
        return new Like(null, userFromId, userToId, isLiked, false);
    }
    @Override
    public Integer id() {
        return likeId;
    }
}
