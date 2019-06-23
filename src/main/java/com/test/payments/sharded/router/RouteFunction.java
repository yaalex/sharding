package com.test.payments.sharded.router;

public interface RouteFunction {
    Long getValue(String key);
}
