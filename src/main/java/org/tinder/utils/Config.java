package org.tinder.utils;

import java.util.Optional;

public class Config {
    enum ConfigKeys {
        PORT("PORT", "8080"),
        DB_URI("DB_HOST", "jdbc:postgresql://tinder-dev.ckej7ilphtof.eu-central-1.rds.amazonaws.com:5432"),
        DB_NAME("DB_NAME", "dev"),
        DB_USER("DB_USER", "postgres"),
        DB_PASSWORD("DB_PASSWORD", "12345678"),
        ACCESS_TOKEN_SECRET_KEY("ACCESS_TOKEN_SECRET", "dSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t");

        private final String key;
        private final String defaultValue;

        ConfigKeys(String key, String defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        public String getKey() {
            return key;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    private static String getEnvByKey(ConfigKeys keys) {
        return Optional.ofNullable(System.getenv(keys.getKey())).orElse(keys.getDefaultValue());
    }

    public static Integer getPort() {
        try {
            return Integer.parseInt(getEnvByKey(ConfigKeys.PORT));
        } catch (NumberFormatException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String getDbConnectionUri() {
        String uri = getEnvByKey(ConfigKeys.DB_URI);
        String name = getEnvByKey(ConfigKeys.DB_NAME);
        return String.format("%s/%s", uri, name);
    }

    public static String getDbUser() {
        return getEnvByKey(ConfigKeys.DB_USER);
    }

    public static String getDbPassword() {
        return getEnvByKey(ConfigKeys.DB_PASSWORD);
    }

    public static String getAccessTokenKey() {
        return getEnvByKey(ConfigKeys.ACCESS_TOKEN_SECRET_KEY);
    }
}
