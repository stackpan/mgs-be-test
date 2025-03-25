package io.github.stackpan.mgs_be_test.model.dto;

import java.util.Optional;

public record CreateUserDto(
    String username,
    String password,
    String email,
    String firstName,
    Optional<String> lastName,
    Optional<String> profilePicture,
    String role
) {
}
