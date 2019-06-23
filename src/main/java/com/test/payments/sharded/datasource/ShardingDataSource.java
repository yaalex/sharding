package com.test.payments.sharded.datasource;

import lombok.Getter;
import lombok.Setter;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

// todo maybe AbstractRoutingDataSource
@Getter
@Setter
public class ShardingDataSource {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public ShardingDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }
}
