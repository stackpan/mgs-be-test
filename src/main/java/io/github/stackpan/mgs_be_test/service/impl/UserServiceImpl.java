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
    public User create(CreateUserDto data) {
        var user = new User();
        user.setUsername(data.username());
        user.setEmail(data.email());
        user.setFirstName(data.firstName());
        user.setLastName(data.lastName().orElse(null));
        user.setPassword(passwordEncoder.encode(data.password()));
        user.setRole(data.role());

        if (data.profilePicture().isPresent()) {
            user.setProfilePicture(storageService.store(data.profilePicture().get()));
        }

        return userRepository.save(user);
    }

}
