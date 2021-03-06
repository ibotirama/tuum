package com.tuum.tuumapi;

import com.tuum.tuumapi.model.Account;
import com.tuum.tuumapi.model.Balance;
import com.tuum.tuumapi.model.Transaction;
import com.tuum.tuumapi.model.TransactionDirection;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MappedTypes({Account.class, Balance.class, Transaction.class, TransactionDirection.class})
@MapperScan("com.tuum.tuumapi.mapppers")
@SpringBootApplication
public class TuumApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TuumApiApplication.class, args);
    }

}
