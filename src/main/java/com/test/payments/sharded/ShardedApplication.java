package com.test.payments.sharded;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShardedApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardedApplication.class, args);
    }

}
