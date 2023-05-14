package org.tinder.services;

public final class Services {
    public final UserService user;

    private Services() {
        this.user = new UserService();
    }

    public static Services create() {
        return new Services();
    }
}
