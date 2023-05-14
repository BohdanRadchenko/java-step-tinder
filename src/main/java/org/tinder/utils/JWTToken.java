package org.tinder.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.UUID;

public class JWTToken {
    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final JWTCreator.Builder builder;
    private final Date issuedAt = new Date();
    private Date expiresAt;

    private JWTToken(String secret) {
        algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm).build();
        builder = JWT.create();
        builder
                .withJWTId(UUID.randomUUID().toString())
                .withIssuedAt(issuedAt);
    }

    private void setExpires(long expires) {
        expiresAt = new Date(issuedAt.getTime() + expires);
        builder.withExpiresAt(expiresAt);
    }

    private DecodedJWT verify(String token) throws RuntimeException {
        try {
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException(e);
        }
    }

    public JWTToken addClaim(String key, String value) {
        builder.withClaim(key, value);
        return this;
    }

    public String sign() {
        return builder.sign(algorithm);
    }

    public static JWTToken of(String secret, long expires) {
        JWTToken tokenWorker = new JWTToken(secret);
        tokenWorker.setExpires(expires);

        return tokenWorker;
    }

    public static JWTToken of(String secret) {
        return new JWTToken(secret);
    }

    public static DecodedJWT verify(String token, String secret) throws RuntimeException {
        JWTToken jwt = JWTToken.of(secret);
        try {
            return jwt.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException(e);
        }
    }
}
