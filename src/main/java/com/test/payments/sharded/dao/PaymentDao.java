package com.test.payments.sharded.dao;

import com.test.payments.sharded.model.Payment;

import java.util.List;

// TODO paging???
public interface PaymentDao {
    List<Payment> getPayments();

    List<Payment> getPaymentsBySender(String sender);

    Payment save(Payment payment);
}
