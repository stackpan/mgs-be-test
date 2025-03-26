package io.github.stackpan.mgs_be_test.service;

import io.github.stackpan.mgs_be_test.entity.User;
import io.github.stackpan.mgs_be_test.model.dto.LoginDto;
import io.github.stackpan.mgs_be_test.model.dto.UpdatePasswordDto;
import io.github.stackpan.mgs_be_test.model.dto.UpdateProfilePictureDto;
import io.github.stackpan.mgs_be_test.model.dto.UserDto;

import java.io.IOException;

public interface AuthService {
    
    String login(LoginDto data);

    User updateMe(UserDto data);

    void updatePassword(UpdatePasswordDto data);

    User updateProfilePicture(UpdateProfilePictureDto data) throws IOException;

}
