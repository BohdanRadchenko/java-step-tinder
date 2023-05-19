package org.tinder.utils;

public final class Constants {
    public static final String DIR_PATH = ResourcesOps.dir("/");
    public static Integer AUTH_EXPIRED_TIME = 24 * 60 * 60 * 1000;
    public static Long MAX_FILE_UPLOAD_SIZE = 1024 * 1024 * 10L;
    public static Long MAX_REQUEST_SIZE = MAX_FILE_UPLOAD_SIZE * 2;
    public static Integer FILE_SIZE_THRESHOLD = 1024 * 1024;
    public static final String MULTIPART_CONFIG_LOCATION = "/";
    public static String STATIC_CONTENT_DIR = "static";
    public static String STATIC_CONTENT_AVATAR_DIR = "avatars";
    public static String STATIC_FILES = String.format("%s%s", DIR_PATH, STATIC_CONTENT_DIR);
    public static String STATIC_FILES_AVATARS = String.format("%s/%s/", STATIC_FILES, STATIC_CONTENT_AVATAR_DIR);
}
