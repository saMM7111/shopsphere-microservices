package com.samm7111.payment.service;

import com.samm7111.payment.model.PaymentRecord;
import com.samm7111.payment.model.request.PaymentRequest;
import java.util.List;

public interface PaymentService {
    PaymentRecord initiate(PaymentRequest request);
    PaymentRecord approve(String reference);
    PaymentRecord byReference(String reference);
    List<PaymentRecord> pending();
}