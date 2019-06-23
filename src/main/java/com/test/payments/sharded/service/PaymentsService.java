package com.test.payments.sharded.service;

import com.test.payments.sharded.dto.PaymentDto;

import java.util.List;

// для простоты конверить туда обратно будем в сервисе
public interface PaymentsService {
    PaymentDto save(PaymentDto payment);

    List<PaymentDto> getPayments();

    List<PaymentDto> getPaymentsForSender(String senderId);

}
