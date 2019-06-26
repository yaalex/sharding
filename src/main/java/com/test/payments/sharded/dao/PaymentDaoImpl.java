package com.test.payments.sharded.dao;

import com.test.payments.sharded.datasource.ShardingDataSource;
import com.test.payments.sharded.mapper.PaymentMapper;
import com.test.payments.sharded.model.Payment;
import com.test.payments.sharded.router.DataSourceRouter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class PaymentDaoImpl implements PaymentDao {
    private static final String SELECT_ALL = "select * from payment";
    private static final String SELECT_ALL_BY_SENDER = SELECT_ALL + " where sender like ? "; // пусть пока так

    @Autowired
    @Qualifier("orderNumberRouter")
    private DataSourceRouter router;

    @Override
    public List<Payment> getPayments() {
        List<Payment> payments = new ArrayList<>();
        for (ShardingDataSource dataSource : router.getAllDataSources()) {
            payments.addAll(dataSource.getJdbcTemplate().query(SELECT_ALL, new PaymentMapper()));
        }
        return payments;
    }

    @Override
    public List<Payment> getPaymentsBySender(String sender) {
        return router.routeDataSource(sender).getJdbcTemplate().query(SELECT_ALL_BY_SENDER, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, sender);
            }
        }, new PaymentMapper());
    }

    @Override
    public Payment save(Payment payment) {
        JdbcTemplate jdbcTemplate = router.routeDataSource(payment.getSender()).getJdbcTemplate();
        Map<String, Object> params = new HashMap<>();
        String id = UUID.randomUUID().toString();
        params.put("id", id); // TODO плохо, но mysql тяжко живет с UUID
        params.put("sender", payment.getSender());
        params.put("receiver", payment.getReceiver());
        params.put("amount", payment.getAmount());
        new SimpleJdbcInsert(jdbcTemplate).withTableName("payment").execute(new MapSqlParameterSource(params));
        payment.setId(id);
        return payment;
    }
}
