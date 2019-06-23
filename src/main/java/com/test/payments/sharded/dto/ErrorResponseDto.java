package com.test.payments.sharded.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class ErrorResponseDto {

    private String timestamp;

    private String message;

    private String error;

    private int status;

}
