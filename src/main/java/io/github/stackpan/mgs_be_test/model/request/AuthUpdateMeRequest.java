package io.github.stackpan.mgs_be_test.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Optional;

@Data
public class AuthUpdateMeRequest {

    @NotNull
    @Size(max = 100)
    String username;

    @NotNull
    @Size(max = 100)
    @Email
    String email;

    @NotNull
    @Size(max = 50)
    String firstName;

    @NotNull
    Optional<@Size(max = 100) String> lastName;

}
