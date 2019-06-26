package com.test.payments.sharded.service;

import com.test.payments.sharded.dao.PaymentDao;
import com.test.payments.sharded.dto.PaymentDto;
import com.test.payments.sharded.model.Payment;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentsServiceImpl implements PaymentsService {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentsServiceImpl.class.getName());
    private ModelMapper mapper;
    private PaymentDao paymentDao;

    @Autowired
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setPaymentDao(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @Override
    public PaymentDto save(PaymentDto paymentDto) {
        Payment payment = mapper.map(paymentDto, Payment.class);
        payment = paymentDao.save(payment);
        return mapper.map(payment, PaymentDto.class);
    }

    @Override
    public List<PaymentDto> getPayments() {
        List<Payment> payments = paymentDao.getPayments();
        return mapper.map(payments, new TypeToken<List<PaymentDto>>() {
        }.getType());
    }

    @Override
    public Long getPaymentsForSender(String senderId) {
        List<Payment> payments = paymentDao.getPaymentsBySender(senderId);
        long sumBySender = payments.stream().map(Payment::getAmount).mapToLong(Long::longValue).sum();
        return sumBySender;
    }
}
