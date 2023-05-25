package org.tinder.services;

public final class Services {
    public final UserService user;
    public final MessageService messageService;
    public final LikeService likeService;

    private Services() {
        this.user = new UserService();
        this.messageService = new MessageService();
        this.likeService = new LikeService();
    }

    public static Services create() {
        return new Services();
    }
}
