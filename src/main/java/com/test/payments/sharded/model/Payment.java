package com.test.payments.sharded.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Payment {
    private Long id; // TODO uuid
    private String sender;
    private String receiver;
    private Long amount;
}
