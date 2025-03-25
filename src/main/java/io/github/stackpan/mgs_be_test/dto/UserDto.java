package io.github.stackpan.mgs_be_test.dto;

import java.util.UUID;

import io.github.stackpan.mgs_be_test.entity.User;

public record UserDto(
    UUID id,
    String username,
    String email,
    String firstName,
    String lastName,
    String profilePicture,
    String role
) {
    public static UserDto fromEntity(User entity) {
        return new UserDto(
            entity.getId(),
            entity.getUsername(),
            entity.getEmail(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getProfilePicture(),
            entity.getRole()
        );
    }
}
