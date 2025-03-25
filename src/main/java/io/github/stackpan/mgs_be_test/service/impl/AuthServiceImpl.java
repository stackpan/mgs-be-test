package io.github.stackpan.mgs_be_test.service.impl;

import io.github.stackpan.mgs_be_test.entity.User;
import io.github.stackpan.mgs_be_test.exception.InvalidDtoException;
import io.github.stackpan.mgs_be_test.model.dto.UpdatePasswordDto;
import io.github.stackpan.mgs_be_test.model.dto.UserDto;
import io.github.stackpan.mgs_be_test.repository.UserRepository;
import io.github.stackpan.mgs_be_test.security.AuthToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.stackpan.mgs_be_test.model.dto.LoginDto;
import io.github.stackpan.mgs_be_test.security.JwtTokenizer;
import io.github.stackpan.mgs_be_test.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final AuthToken authToken;

    private final JwtTokenizer jwtTokenizer;

    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginDto data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
        var authenticated = authenticationManager.authenticate(authenticationToken);

        return jwtTokenizer.generate(authenticated);
    }

    @Override
    @Transactional
    public User updateMe(UserDto data) {
        var foundUsers = userRepository.findByUsernameOrEmail(data.getUsername(), data.getEmail());

        var currentUser = userRepository.findById(authToken.getCurrentSubject())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));

        var invalidDtoException = new InvalidDtoException();

        if (foundUsers.stream().anyMatch(user -> user.getUsername().equals(data.getUsername()) && !user.getId().equals(currentUser.getId()))) {
            invalidDtoException.putDetail("username", "username is already in use");
        }

        if (foundUsers.stream().anyMatch(user -> user.getEmail().equals(data.getEmail()) && !user.getId().equals(currentUser.getId()))) {
            invalidDtoException.putDetail("email", "email is already in use");
        }

        if (!invalidDtoException.getDetails().isEmpty()) throw invalidDtoException;

        currentUser.setUsername(data.getUsername());
        currentUser.setEmail(data.getEmail());
        currentUser.setFirstName(data.getFirstName());
        currentUser.setLastName(data.getLastName().orElse(null));

        return userRepository.save(currentUser);
    }

    @Override
    @Transactional
    public void updatePassword(UpdatePasswordDto data) {
        var currentUser = userRepository.findById(authToken.getCurrentSubject())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));

        if (!passwordEncoder.matches(data.getCurrentPassword(), currentUser.getPassword())) {
            throw new InvalidDtoException("currentPassword", "currentPassword is incorrect");
        }

        currentUser.setPassword(passwordEncoder.encode(data.getNewPassword()));

        userRepository.save(currentUser);
    }
}
