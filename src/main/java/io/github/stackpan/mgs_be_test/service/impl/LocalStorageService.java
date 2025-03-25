package io.github.stackpan.mgs_be_test.service.impl;

import io.github.stackpan.mgs_be_test.exception.InvalidFileTypeException;
import io.github.stackpan.mgs_be_test.exception.InvalidStorageUploadException;
import io.github.stackpan.mgs_be_test.service.StorageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class LocalStorageService implements StorageService {

    @Value("${storage.upload_dir:uploads}")
    private String UPLOAD_DIR;

    private final Pattern DATA_URI_BASE64_PATTERN = Pattern.compile("data:(.+);base64,(.+)");

    @PostConstruct
    public void init() {
        var uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();
    }

    @Override
    public String store(String dataURIEncoded, String fieldNameContext, int maxSize, String... allowedExtensions) {
        var matched = DATA_URI_BASE64_PATTERN.matcher(dataURIEncoded);

        matched.find();

        var mimeType = matched.group(1);
        var fileExtension = getExtension(mimeType);
        var base64Data = matched.group(2);

        if (allowedExtensions.length > 0 && !Set.of(allowedExtensions).contains(fileExtension)) {
            throw new InvalidFileTypeException(fieldNameContext, allowedExtensions);
        }

        var fileBytes = Base64.getDecoder().decode(base64Data);

        if (fileBytes.length > maxSize) {
            throw new MaxUploadSizeExceededException(maxSize);
        }

        var filename = "%s.%s".formatted(UUID.randomUUID(), fileExtension);
        var fullPath = Path.of(UPLOAD_DIR, filename);

        var file = new File(fullPath.toString());
        try (var fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(fileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return filename;
    }

    private static String getExtension(String mimeType) {
        String[] parts = mimeType.split("/");
        return parts.length == 2 ? parts[1] : "";
    }
}
