package com.test.payments.sharded.dto;

import lombok.Data;

@Data
public class PaymentDto {
    private Long id;
    private String sender;
    private String receiver;
    private Long amount;
}
