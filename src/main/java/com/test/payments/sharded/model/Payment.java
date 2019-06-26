package com.test.payments.sharded.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private String id; // TODO uuid
    private String sender;
    private String receiver;
    private Long amount;
}
