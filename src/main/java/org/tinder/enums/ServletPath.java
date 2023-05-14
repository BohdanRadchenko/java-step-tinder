package org.tinder.enums;

public enum ServletPath {
    STATIC("/static/*"),
    HOME("/"),
    LOGIN("/login"),
    REGISTER("/register"),
    LOGOUT("/logout");

    private final String path;

    ServletPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
