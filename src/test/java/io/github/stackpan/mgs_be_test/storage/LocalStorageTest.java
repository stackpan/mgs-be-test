package io.github.stackpan.mgs_be_test.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.github.stackpan.mgs_be_test.service.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.stackpan.mgs_be_test.utils.FileUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LocalStorageTest {

    @Autowired
    private StorageService storageService;
    
    @Test
    void storeFromDataURIBase64() throws IOException {
        var imagePath = Paths.get("src/test/resources/photo.jpg");

        var dataURIEncoded = FileUtils.dataURIEncode(imagePath);

        var storedFilename = storageService.store(dataURIEncoded, 128000, "png", "jpg", "jpeg");

        var path = Paths.get("uploads/" + storedFilename);

        assertTrue(Files.exists(path));

        Files.deleteIfExists(path);
    }

    @Test
    void storeFromDataURIBase64InBigFileShouldError() throws IOException {
        var imagePath = Paths.get("src/test/resources/photo-big.jpg");

        var dataURIEncoded = FileUtils.dataURIEncode(imagePath);

        assertThrows(MaxUploadSizeExceededException.class, () -> storageService.store(dataURIEncoded, 128000, "jpg", "jpeg"));
    }

}
