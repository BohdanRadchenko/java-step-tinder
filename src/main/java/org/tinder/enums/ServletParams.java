package org.tinder.enums;

public enum ServletParams {
    EMAIL("email"),
    PASSWORD("password");

    private final String param;

    ServletParams(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}
