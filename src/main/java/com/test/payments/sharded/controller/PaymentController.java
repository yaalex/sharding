package com.test.payments.sharded.controller;

import com.test.payments.sharded.dto.PaymentDto;
import com.test.payments.sharded.service.PaymentsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// TODO накрутить security
@RestController
@RequestMapping(path = "/payments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

    private PaymentsService service;

    @Autowired
    public void setService(PaymentsService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody PaymentDto paymentDto) {
        PaymentDto dto = service.save(paymentDto);
        return ResponseEntity.ok().body(dto);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getPayments() {
        return ResponseEntity.ok(service.getPayments());
    }

    @RequestMapping(path = "/sender/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> getPaymentsForSender(@PathVariable("name") String sender) {
        return ResponseEntity.ok(service.getPaymentsForSender(sender));
    }

}
