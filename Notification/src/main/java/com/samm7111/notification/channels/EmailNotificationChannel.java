package com.samm7111.notification.channels;

import com.samm7111.notification.model.NotificationMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationChannel implements NotificationChannel {

    @Override
    public String name() {
        return "EMAIL";
    }

    @Override
    public String deliver(NotificationMessage message) {
        return "Email queued for " + message.recipient();
    }
}