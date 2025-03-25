package io.github.stackpan.mgs_be_test.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.Optional;

@Value
public class AuthRegisterRequest {

    @NotNull
    @Size(max = 100)
    String username;

    @NotNull
    @Size(max = 50)
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
            message = "{validation.constraints.Pattern.ValidPassword.message}"
    )
    String password;

    @NotNull
    @Size(max = 100)
    String email;

    @NotNull
    @Size(max = 50)
    String firstName;

    @NotNull
    Optional<@Size(max = 100) String> lastName;

    @NotNull
    Optional<String> profilePicture;

    @NotNull
    String role;
}
