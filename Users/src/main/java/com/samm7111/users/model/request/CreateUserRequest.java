package com.samm7111.users.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
    @NotBlank @Email String email,
    @NotBlank String fullName,
    @NotBlank String password,
    @NotBlank String role
) {
}