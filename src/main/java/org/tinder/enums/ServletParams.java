package org.tinder.enums;

public enum ServletParams {
    LOGIN("login"),
    EMAIL("email"),
    PASSWORD("password"),
    PASSWORD_CONFIRM("password_confirm"),
    REGISTER_LINK(ServletPath.REGISTER.path()),
    LOGIN_LINK(ServletPath.LOGIN.path()),
    LOGOUT_LINK(ServletPath.LOGOUT.path()),
    USERS_LINK(ServletPath.USERS.path()),
    LIKED_LINK(ServletPath.LIKED.path()),
    CURRENT_USER("CURRENT_USER");
    private final String param;

    ServletParams(String param) {
        this.param = param;
    }

    public String param() {
        return param;
    }
}
