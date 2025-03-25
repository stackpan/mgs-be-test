package io.github.stackpan.mgs_be_test.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class AuthMeUpdatePasswordRequest {

    @NotNull
    @Size(max = 50)
    String currentPassword;

    @NotNull
    @Size(max = 50)
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
            message = "must be at least 8 characters, 1 uppercase letter, 1 lowercase letter, 1 number, and contains special characters"
    )
    String newPassword;

}
