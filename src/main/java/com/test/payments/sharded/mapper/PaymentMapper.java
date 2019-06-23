package com.test.payments.sharded.mapper;

import com.test.payments.sharded.model.Payment;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentMapper implements RowMapper<Payment> {
    @Override
    public Payment mapRow(ResultSet resultSet, int i) throws SQLException {
        Long id = resultSet.getLong("id");
        String sender = resultSet.getString("sender");
        String receiver = resultSet.getString("receiver");
        Long amount = resultSet.getLong("amount");
        return new Payment(id, sender, receiver, amount);
    }
}
