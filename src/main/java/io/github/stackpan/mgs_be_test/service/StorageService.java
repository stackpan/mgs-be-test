package io.github.stackpan.mgs_be_test.service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface StorageService {

    String store(String dataURIEncoded, String fieldNameContext, int maxSize, String... allowedExtensions) throws FileNotFoundException;

    void delete(String path) throws IOException;

}
