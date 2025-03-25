package io.github.stackpan.mgs_be_test.model.dto;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import io.github.stackpan.mgs_be_test.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    UUID id;
    String username;
    String email;
    String firstName;
    Optional<String> lastName;
    Optional<String> profilePicture;
    String role;
    Set<String> permissions;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(Optional.ofNullable(user.getLastName()))
                .profilePicture(Optional.ofNullable(user.getProfilePicture()))
                .role(user.getRole())
                .permissions(user.getPermissions())
                .build();
    }
}
