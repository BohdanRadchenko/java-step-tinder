package org.tinder.services;

import org.tinder.utils.Database;

import java.sql.Connection;

public final class Services {
    public final UserService user;

    private Services() {
        this.user = new UserService();
    }

    public static Services create() {
        return new Services();
    }
}