package com.samm7111.notification.controller;

import com.samm7111.notification.model.NotificationMessage;
import com.samm7111.notification.model.request.NotificationRequest;
import com.samm7111.notification.service.NotificationService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> send(@Valid @RequestBody NotificationRequest request) {
        NotificationMessage message = new NotificationMessage(
            request.recipient(),
            request.subject(),
            request.body(),
            request.channel());
        String status = notificationService.send(message);
        return ResponseEntity.ok(Map.of("status", status));
    }
}