package org.tinder.enums;

public enum ServletPath {
    STATIC("/static/*"),
    HOME("/"),
    LOGIN("/login"),
    REGISTER("/register"),
    LOGOUT("/logout"),
    MESSAGES("/messages/*");

    private final String path;

    ServletPath(String path) {
        this.path = path;
    }

    public String path() {
        return this.path;
    }
}
