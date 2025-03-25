package io.github.stackpan.mgs_be_test.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.stackpan.mgs_be_test.dto.LoginDto;
import io.github.stackpan.mgs_be_test.dto.UserDto;
import io.github.stackpan.mgs_be_test.service.AuthService;
import io.github.stackpan.mgs_be_test.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginDto request) {
        var token = authService.login(request);
        
        return Map.of("token", token);
    }

    @GetMapping("/me")
    public UserDto me() {
        return UserDto.fromEntity(userService.getMe());
    }

}
