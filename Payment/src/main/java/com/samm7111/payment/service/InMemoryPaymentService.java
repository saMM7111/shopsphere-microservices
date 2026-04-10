package com.samm7111.payment.service;

import com.samm7111.payment.model.PaymentRecord;
import com.samm7111.payment.model.request.PaymentRequest;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class InMemoryPaymentService implements PaymentService {

    private final Map<String, PaymentRecord> records = new ConcurrentHashMap<>();

    @Override
    public PaymentRecord initiate(PaymentRequest request) {
        String reference = "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        PaymentRecord record = new PaymentRecord(reference, request.orderNumber(), request.amount(), request.method(), "PENDING", Instant.now());
        records.put(reference, record);
        return record;
    }

    @Override
    public PaymentRecord approve(String reference) {
        PaymentRecord existing = byReference(reference);
        PaymentRecord approved = new PaymentRecord(existing.reference(), existing.orderNumber(), existing.amount(), existing.method(), "APPROVED", existing.createdAt());
        records.put(reference, approved);
        return approved;
    }

    @Override
    public PaymentRecord byReference(String reference) {
        PaymentRecord record = records.get(reference);
        if (record == null) {
            throw new IllegalArgumentException("Payment not found");
        }
        return record;
    }

    @Override
    public List<PaymentRecord> pending() {
        return records.values().stream().filter(r -> "PENDING".equals(r.status())).toList();
    }
}