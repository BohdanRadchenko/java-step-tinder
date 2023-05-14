package org.tinder.utils;

import org.tinder.enums.CookieNames;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

public final class CookieWorker {
    public static Optional<String> tryGetCookie(Cookie[] cookies, CookieNames cookieNames) {
        if (cookies == null) return Optional.empty();
        return Arrays.stream(cookies)
                .filter(c -> cookieNames.equals(c.getName()))
                .findAny()
                .map(Cookie::getValue);
    }

    public static String getCookieOrThrow(HttpServletRequest req, CookieNames cookieNames) {
        Cookie[] cookies = req.getCookies();
        return tryGetCookie(cookies, cookieNames)
                .orElseThrow(() -> new RuntimeException("Cookie is absent"));
    }

    public static void auth(HttpServletResponse res, String token) {
        Cookie cookie = new Cookie(CookieNames.AUTH_TOKEN.getValue(), token);
        cookie.setMaxAge(Constants.AUTH_EXPIRED_TIME);
        res.addCookie(cookie);
        res.addHeader("Access-Control-Expose-Headers", "Set-Cookie");
    }

    public static void logout(HttpServletResponse res) {
        Cookie cookie = new Cookie(CookieNames.AUTH_TOKEN.getValue(), "");
        cookie.setMaxAge(0);
        res.addCookie(cookie);
    }
}
