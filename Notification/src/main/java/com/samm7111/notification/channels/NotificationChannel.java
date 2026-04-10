package com.samm7111.notification.channels;

import com.samm7111.notification.model.NotificationMessage;

public interface NotificationChannel {
    String name();
    String deliver(NotificationMessage message);
}