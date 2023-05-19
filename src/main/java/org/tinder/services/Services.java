package org.tinder.services;

public final class Services {
    public final UserService user;
    public final MessageService messageService;

    private Services() {
        this.user = new UserService();
        this.messageService = new MessageService();
    }

    public static Services create() {
        return new Services();
    }
}
