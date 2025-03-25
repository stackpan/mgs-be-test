package io.github.stackpan.mgs_be_test.model.request;

public record AuthRegisterRequest(
    String username,
    String password,
    String email,
    String firstName,
    String lastName,
    String profilePicture,
    String role
) {
}
