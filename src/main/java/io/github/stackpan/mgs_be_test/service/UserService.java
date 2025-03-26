package io.github.stackpan.mgs_be_test.service;

import io.github.stackpan.mgs_be_test.entity.User;
import io.github.stackpan.mgs_be_test.model.dto.CreateUserDto;

import java.io.FileNotFoundException;
import java.util.UUID;

public interface UserService {

    User create(CreateUserDto data) throws FileNotFoundException;

    User updatePermissionsById(UUID userId, String[] permissions);
}
