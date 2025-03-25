package io.github.stackpan.mgs_be_test.storage;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.stackpan.mgs_be_test.utils.FileUtils;

@SpringBootTest
public class LocalStorageTest {

    @Autowired
    private LocalStorage localStorage;
    
    @Test
    void storeFromDataURIBase64() throws IOException {
        var imagePath = Paths.get("src/test/resources/photo.jpg");

        var dataURIEncoded = FileUtils.dataURIEncode(imagePath);

        localStorage.store(dataURIEncoded);
    }

}
