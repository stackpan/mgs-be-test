package io.github.stackpan.mgs_be_test.service;

import io.github.stackpan.mgs_be_test.entity.User;
import io.github.stackpan.mgs_be_test.model.dto.CreateUserDto;

public interface UserService {

    User getMe();

    User create(CreateUserDto data);
}
