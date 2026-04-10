package com.samm7111.notification.model.request;

import jakarta.validation.constraints.NotBlank;

public record NotificationRequest(
    @NotBlank String recipient,
    @NotBlank String subject,
    @NotBlank String body,
    @NotBlank String channel
) {
}