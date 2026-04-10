package com.samm7111.notification.channels;

import com.samm7111.notification.model.NotificationMessage;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificationChannel implements NotificationChannel {

    @Override
    public String name() {
        return "SMS";
    }

    @Override
    public String deliver(NotificationMessage message) {
        return "SMS queued for " + message.recipient();
    }
}