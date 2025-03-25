package io.github.stackpan.mgs_be_test.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class FileUtils {
    
    public static String dataURIEncode(Path path) throws IOException {
        return "data:%s;base64,%s".formatted(getMimeType(path), base64Encode(path));
    }

    public static String base64Encode(Path path) throws IOException {
        byte[] pathByte = Files.readAllBytes(path);
        return Base64.getEncoder().encodeToString(pathByte);
    }

    public static String getMimeType(Path path) {
        try {
            String mimeType = Files.probeContentType(path);
            return mimeType != null ? mimeType : "application/octet-stream";
        } catch (IOException e) {
            return "application/octet-stream";
        }
    }

}
