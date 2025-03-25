package io.github.stackpan.mgs_be_test.service;

import java.io.FileNotFoundException;

public interface StorageService {

    String store(String dataURIEncoded, int maxSize, String... allowedExtensions) throws FileNotFoundException;

}
