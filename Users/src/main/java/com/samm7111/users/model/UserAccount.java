package com.samm7111.users.model;

import java.time.Instant;

public record UserAccount(
    String email,
    String fullName,
    String encodedPassword,
    String role,
    Instant createdAt
) {
}