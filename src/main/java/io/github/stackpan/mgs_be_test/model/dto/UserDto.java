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

    private UUID id;

    private String username;

    private String email;

    private String firstName;

    private Optional<String> lastName;

    private Optional<String> profilePicture;

    private String role;

    private Set<String> permissions;

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
