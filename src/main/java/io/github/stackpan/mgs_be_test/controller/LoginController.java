package io.github.stackpan.mgs_be_test.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.stackpan.mgs_be_test.dto.LoginDto;
import io.github.stackpan.mgs_be_test.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;

    @PostMapping
    public Map<String, Object> login(@RequestBody LoginDto request) {
        var token = authService.login(request);
        
        return Map.of("token", token);
    }

}
