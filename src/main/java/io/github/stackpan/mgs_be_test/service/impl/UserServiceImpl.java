package io.github.stackpan.mgs_be_test.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.github.stackpan.mgs_be_test.entity.User;
import io.github.stackpan.mgs_be_test.repository.UserRepository;
import io.github.stackpan.mgs_be_test.security.AuthToken;
import io.github.stackpan.mgs_be_test.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final AuthToken authToken;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user with username: %s".formatted(username)));
    }

    @Override
    public User getMe() {
        return userRepository.findById(authToken.getCurrentSubject())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
    }

}
