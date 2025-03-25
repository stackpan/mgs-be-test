package io.github.stackpan.mgs_be_test.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class CreateUserDto {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private Optional<String> lastName;
    private Optional<String> profilePicture;
    private String role;
}

