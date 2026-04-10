package com.samm7111.payment.controller;

import com.samm7111.payment.model.PaymentRecord;
import com.samm7111.payment.model.request.PaymentRequest;
import com.samm7111.payment.service.PaymentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/initiate")
    public ResponseEntity<PaymentRecord> initiate(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.initiate(request));
    }

    @PostMapping("/approve/{reference}")
    public ResponseEntity<PaymentRecord> approve(@PathVariable String reference) {
        return ResponseEntity.ok(paymentService.approve(reference));
    }

    @GetMapping("/status/{reference}")
    public ResponseEntity<PaymentRecord> byReference(@PathVariable String reference) {
        return ResponseEntity.ok(paymentService.byReference(reference));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<PaymentRecord>> pending() {
        return ResponseEntity.ok(paymentService.pending());
    }
}