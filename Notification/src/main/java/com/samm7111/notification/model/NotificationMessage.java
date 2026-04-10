package com.samm7111.notification.model;

public record NotificationMessage(
    String recipient,
    String subject,
    String body,
    String channel
) {
}