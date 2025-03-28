package io.github.stackpan.mgs_be_test.controller;

import io.github.stackpan.mgs_be_test.model.dto.UpdateUserPermissionDto;
import io.github.stackpan.mgs_be_test.model.dto.UserDto;
import io.github.stackpan.mgs_be_test.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserDto getUser(@PathVariable UUID userId) {
        var user = userService.getById(userId);

        return UserDto.fromEntity(user);
    }

    @PostMapping("/{userId}/permissions")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserDto updatePermissions(@PathVariable UUID userId, @RequestBody @Valid UpdateUserPermissionDto request) {
        var user = userService.updatePermissionsById(userId, request.getPermissions().toArray(new String[0]));

        return UserDto.fromEntity(user);
    }

}
