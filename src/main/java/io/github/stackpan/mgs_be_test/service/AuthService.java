package io.github.stackpan.mgs_be_test.service;

import io.github.stackpan.mgs_be_test.model.dto.LoginDto;

public interface AuthService {
    
    String login(LoginDto data);

}
