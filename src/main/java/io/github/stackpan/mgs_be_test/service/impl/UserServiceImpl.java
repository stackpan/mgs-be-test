package io.github.stackpan.mgs_be_test.service.impl;

import io.github.stackpan.mgs_be_test.service.StorageService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.stackpan.mgs_be_test.entity.User;
import io.github.stackpan.mgs_be_test.model.dto.CreateUserDto;
import io.github.stackpan.mgs_be_test.repository.UserRepository;
import io.github.stackpan.mgs_be_test.service.UserService;
import lombok.RequiredArgsConstructor;

import java.io.FileNotFoundException;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final StorageService storageService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user with username: %s".formatted(username)));
    }

    @Override
    public User getById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user with id: %s".formatted(userId.toString())));
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

    @Override
    @Transactional
    public User updatePermissionsById(UUID userId, String... permissions) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user with id: %s".formatted(userId.toString())));

        user.getPermissions().clear();
        user.getPermissions().addAll(Set.of(permissions));

        return userRepository.save(user);
    }

}
