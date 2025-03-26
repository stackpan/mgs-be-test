package io.github.stackpan.mgs_be_test.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class AuthLoginRequest {

    @NotNull
    @Size(max = 100)
    String username;

    @NotNull
    @Size(max = 50)
    String password;

}
