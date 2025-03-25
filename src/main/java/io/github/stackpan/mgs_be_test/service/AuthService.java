package io.github.stackpan.mgs_be_test.service;

import io.github.stackpan.mgs_be_test.entity.User;
import io.github.stackpan.mgs_be_test.model.dto.LoginDto;
import io.github.stackpan.mgs_be_test.model.dto.UpdatePasswordDto;
import io.github.stackpan.mgs_be_test.model.dto.UserDto;

public interface AuthService {
    
    String login(LoginDto data);

    User updateMe(UserDto data);

    void updatePassword(UpdatePasswordDto data);

}
