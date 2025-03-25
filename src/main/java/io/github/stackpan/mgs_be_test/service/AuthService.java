package io.github.stackpan.mgs_be_test.service;

import io.github.stackpan.mgs_be_test.dto.LoginDto;

public interface AuthService {
    
    String login(LoginDto data);

}
