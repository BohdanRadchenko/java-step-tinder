package org.tinder.enums;

public enum CookieNames {
    AUTH_TOKEN("AUTH_TOKEN");

    private final String value;

    CookieNames(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean equals(String cookieName) {
        return cookieName.equals(value);
    }


}
