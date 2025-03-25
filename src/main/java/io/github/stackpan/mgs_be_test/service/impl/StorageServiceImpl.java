package io.github.stackpan.mgs_be_test.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.stackpan.mgs_be_test.service.StorageService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    // @Value("${storage.upload_dir}")
    private String UPLOAD_DIR = "uploads";

    private final Pattern DATA_URI_BASE64_PATTERN = Pattern.compile("^data:(.+);base64,(.+)$");

    @Override
    public String store(String dataURIEncoded) {
        try {
            var matcher = DATA_URI_BASE64_PATTERN.matcher(dataURIEncoded);

            var mimeType = matcher.group(1);
            var base64Data = matcher.group(2);

            var fileBytes = Base64.getDecoder().decode(base64Data);

            var uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            var filename = Path.of(UPLOAD_DIR, UUID.randomUUID().toString(), getExtension(mimeType));

            var file = new File(filename.toString());
            try (var fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(fileBytes);
            }

            return filename.toString();
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
