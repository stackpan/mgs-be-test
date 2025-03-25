package io.github.stackpan.mgs_be_test.model.dto;

public record CreateUserDto(
    String username,
    String password,
    String email,
    String firstName,
    String lastName,
    String profilePicture,
    String role
) {
}
