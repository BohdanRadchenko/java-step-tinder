package org.tinder.utils;

import java.net.URISyntaxException;
import java.util.Objects;

public class ResourcesOps {
    public static String dir(String dir) {
        try {
            String path = Objects.requireNonNull(ResourcesOps.class
                            .getClassLoader()
                            .getResource(dir))
                    .toURI()
                    .getPath();
            if (path.startsWith("/")) path = path.substring(1);
            return path;
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Requested path `%s`not found", dir), e);
        }
    }

}
