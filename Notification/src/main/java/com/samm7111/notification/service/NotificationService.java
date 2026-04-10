package com.samm7111.notification.service;

import com.samm7111.notification.channels.NotificationChannel;
import com.samm7111.notification.model.NotificationMessage;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final Map<String, NotificationChannel> channels;

    public NotificationService(List<NotificationChannel> channels) {
        this.channels = channels.stream().collect(Collectors.toMap(c -> c.name().toUpperCase(), Function.identity()));
    }

    public String send(NotificationMessage message) {
        NotificationChannel channel = channels.get(message.channel().toUpperCase());
        if (channel == null) {
            throw new IllegalArgumentException("Unsupported channel: " + message.channel());
        }
        return channel.deliver(message);
    }
}