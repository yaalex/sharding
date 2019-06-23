package com.test.payments.sharded.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@ToString
public class PaymentException extends Exception {

    private final HttpStatus httpStatus;

    /**
     * Creates exception with default status 500
     * @param message
     */
    public PaymentException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
