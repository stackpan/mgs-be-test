package io.github.stackpan.mgs_be_test.service.impl;

import io.github.stackpan.mgs_be_test.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class LocalStorageService implements StorageService {

    @Value("${storage.upload_dir:uploads}")
    private String UPLOAD_DIR;

    private final Pattern DATA_URI_BASE64_PATTERN = Pattern.compile("data:(.+);base64,(.+)");

    public String store(String dataURIEncoded) {
        try {
            var matched = DATA_URI_BASE64_PATTERN.matcher(dataURIEncoded);

            matched.find();

            var mimeType = matched.group(1);
            var base64Data = matched.group(2);

            var fileBytes = Base64.getDecoder().decode(base64Data);

            var uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            var filename = UUID.randomUUID() + getExtension(mimeType);
            var fullPath = Path.of(UPLOAD_DIR, filename);

            var file = new File(fullPath.toString());
            try (var fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(fileBytes);
            }

            return filename;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getExtension(String mimeType) {
        String[] parts = mimeType.split("/");
        return parts.length == 2 ? "." + parts[1] : "";
    }
}
