package org.tinder.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public final class Encryptor {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final Integer ITERATION_COUNT = 65536;
    private static final int SIZE = 128;
    private final byte[] salt;

    public Encryptor(byte[] salt) {
        this.salt = salt;
    }

    public Encryptor(String securityKey) {
        this(securityKey.getBytes());
    }

    public String hash(String password) {
        byte[] pass = Encryptor.pbkdf2(password.toCharArray(), salt);
        Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
        return enc.encodeToString(pass);
    }

    public boolean check(String candidate, String hash) {
        String hashedPass = hash(candidate);
        return hashedPass.equals(hash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt) {
        KeySpec spec = new PBEKeySpec(password, salt, ITERATION_COUNT, SIZE);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Missing algorithm: " + ALGORITHM, ex);
        } catch (InvalidKeySpecException ex) {
            throw new IllegalStateException("Invalid SecretKeyFactory", ex);
        }
    }

    public static Encryptor of(String securityKey) {
        return new Encryptor(securityKey);
    }
}