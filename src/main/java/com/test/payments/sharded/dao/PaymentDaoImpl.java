package com.test.payments.sharded.dao;

import com.test.payments.sharded.datasource.ShardingDataSource;
import com.test.payments.sharded.mapper.PaymentMapper;
import com.test.payments.sharded.model.Payment;
import com.test.payments.sharded.router.DataSourceRouter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class PaymentDaoImpl extends JdbcDaoSupport implements PaymentDao {
    private static final String SELECT_ALL = "select * from payment";
    private static final String SELECT_ALL_BY_SENDER = SELECT_ALL + " where sender = ? ";

    @Autowired
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
        return router.routeDataSource(sender).getJdbcTemplate().query(SELECT_ALL_BY_SENDER, new PaymentMapper(), sender);
    }

    @Transactional
    @Override
    public Payment save(Payment payment) {
        JdbcTemplate jdbcTemplate = router.routeDataSource(payment.getSender()).getJdbcTemplate();
        Map<String, Object> params = new HashMap<>();
        params.put("sender", payment.getSender());
        params.put("receiver", payment.getReceiver());
        params.put("amount", payment.getAmount());
        Long id =
                (Long) new SimpleJdbcInsert(jdbcTemplate).withTableName("payments").usingGeneratedKeyColumns("id")
                        .executeAndReturnKey(new MapSqlParameterSource(params));
        payment.setId(id);
        return payment;
    }
}
