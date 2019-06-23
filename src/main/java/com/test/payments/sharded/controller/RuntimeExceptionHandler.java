package com.test.payments.sharded.controller;

import com.test.payments.sharded.dto.ErrorResponseDto;
import com.test.payments.sharded.exception.PaymentException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class RuntimeExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RuntimeExceptionHandler.class);

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto unknownException(Exception ex, WebRequest req) {
        LOG.error("Catched exception:", ex);
        return new ErrorResponseDto(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT), ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(), 500);
    }

    @ExceptionHandler(value = { AccessDeniedException.class })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDto accessDeniedException(Exception ex, WebRequest req) {
        LOG.error("Catched exception:", ex);
        return new ErrorResponseDto(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT), ex.getMessage(),
                HttpStatus.FORBIDDEN.name(), HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(value = { PaymentException.class })
    public ResponseEntity<?> cardsMobileException(PaymentException ex, WebRequest req) {
        LOG.error("Catched CardsMobileException:", ex);
        return new ResponseEntity<>(new ErrorResponseDto(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT), ex.getMessage(), ex
                .getHttpStatus().name(), ex.getHttpStatus().value()), ex.getHttpStatus());
    }

}
