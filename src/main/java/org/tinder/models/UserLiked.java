package org.tinder.models;

import java.time.LocalDateTime;

public record UserLiked(User user, LocalDateTime lastLogin)  {
}
