package com.samm7111.users.model.response;

public record AuthResponse(
    String email,
    String role,
    String message
) {
}