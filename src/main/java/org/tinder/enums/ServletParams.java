package org.tinder.enums;

public enum ServletParams {
    LOGIN("login"),
    EMAIL("email"),
    PASSWORD("password"),
    PASSWORD_CONFIRM("password_confirm"),
    REGISTER_LINK(ServletPath.REGISTER.path()),
    LOGIN_LINK(ServletPath.LOGIN.path());
    private final String param;

    ServletParams(String param) {
        this.param = param;
    }

    public String param() {
        return param;
    }
}
