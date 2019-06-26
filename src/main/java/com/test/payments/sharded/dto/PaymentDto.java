package com.test.payments.sharded.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentDto {
    private String id;
    private String sender;
    private String receiver;
    private Long amount;
}
