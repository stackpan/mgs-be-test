package io.github.stackpan.mgs_be_test.controller;

import java.io.FileNotFoundException;
import java.util.Map;

import io.github.stackpan.mgs_be_test.model.dto.UpdatePasswordDto;
import io.github.stackpan.mgs_be_test.model.request.AuthMeUpdatePasswordRequest;
import io.github.stackpan.mgs_be_test.model.request.AuthUpdateMeRequest;
import io.github.stackpan.mgs_be_test.model.request.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.stackpan.mgs_be_test.model.dto.CreateUserDto;
import io.github.stackpan.mgs_be_test.model.dto.LoginDto;
import io.github.stackpan.mgs_be_test.model.dto.UserDto;
import io.github.stackpan.mgs_be_test.model.request.AuthRegisterRequest;
import io.github.stackpan.mgs_be_test.service.AuthService;
import io.github.stackpan.mgs_be_test.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid AuthRegisterRequest request) throws FileNotFoundException {
        var dto = CreateUserDto.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .profilePicture(request.getProfilePicture())
                .role(request.getRole())
                .build();

        var user = userService.create(dto);

        return UserDto.fromEntity(user);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody @Valid LoginRequest request) {
        var dto = LoginDto.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();

        var token = authService.login(dto);

        return Map.of("token", token);
    }

    @GetMapping("/me")
    public UserDto me() {
        return UserDto.fromEntity(userService.getMe());
    }

    @PutMapping("/me")
    public UserDto updateMe(@RequestBody @Valid AuthUpdateMeRequest request) {
        var dto = UserDto.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        var user = authService.updateMe(dto);

        return UserDto.fromEntity(user);
    }

    @PostMapping("me/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody @Valid AuthMeUpdatePasswordRequest request) {
        var dto = UpdatePasswordDto.builder()
                .currentPassword(request.getCurrentPassword())
                .newPassword(request.getNewPassword())
                .build();

        authService.updatePassword(dto);

        return ResponseEntity.ok().build();
    }
}
