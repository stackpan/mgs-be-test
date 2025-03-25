package io.github.stackpan.mgs_be_test.service.impl;

import io.github.stackpan.mgs_be_test.service.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import io.github.stackpan.mgs_be_test.entity.User;
import io.github.stackpan.mgs_be_test.model.dto.CreateUserDto;
import io.github.stackpan.mgs_be_test.repository.UserRepository;
import io.github.stackpan.mgs_be_test.security.AuthToken;
import io.github.stackpan.mgs_be_test.service.UserService;
import lombok.RequiredArgsConstructor;

import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final AuthToken authToken;

    private final PasswordEncoder passwordEncoder;

    private final StorageService storageService;

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

    @Override
    @Transactional
    public User create(CreateUserDto data) throws FileNotFoundException {
        var user = new User();
        user.setUsername(data.getUsername());
        user.setEmail(data.getEmail());
        user.setFirstName(data.getFirstName());
        user.setLastName(data.getLastName().orElse(null));
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setRole(data.getRole());

        if (data.getProfilePicture().isPresent()) {
            user.setProfilePicture(storageService.store(data.getProfilePicture().get(), "profilePicture", 1024000, "jpg", "png", "jpeg"));
        }

        return userRepository.save(user);
    }

}
