package io.github.stackpan.mgs_be_test.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import io.github.stackpan.mgs_be_test.dto.LoginDto;
import io.github.stackpan.mgs_be_test.security.JwtTokenizer;
import io.github.stackpan.mgs_be_test.service.AuthService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenizer jwtTokenizer;

    @Override
    public String login(LoginDto data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var authenticated = authenticationManager.authenticate(authenticationToken);

        return jwtTokenizer.generate(authenticated);
    }
}
