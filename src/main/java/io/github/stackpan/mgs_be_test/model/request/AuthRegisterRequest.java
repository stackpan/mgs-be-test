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
            message = "must be at least 8 characters, 1 uppercase letter, 1 lowercase letter, 1 number, and contains special characters"
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
    Optional<
            @Pattern(
                    regexp = "^data:([a-zA-Z0-9+/.-]+/[a-zA-Z0-9+/.-]+);base64,(.+)$",
                    message = "must be a valid base64 Data URI format"
            ) String
            > profilePicture;

    @NotNull
    String role;
}
