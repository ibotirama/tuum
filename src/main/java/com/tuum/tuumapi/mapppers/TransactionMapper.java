package com.tuum.tuumapi.mapppers;

import com.tuum.tuumapi.model.Transaction;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TransactionMapper {

    @Insert("insert into transaction(transaction_id, account_id, amount, currency_code, direction, description, balance) values(#{transactionId}, #{accountId}, #{amount}, #{currency}, #{direction}, #{description}, #{balance})")
    void create(Transaction transaction);

    @Select("select transaction_id, account_id, amount, currency_code, trim(direction), description, balance from transaction where account_id = #{accountId}")
    List<Transaction> findByAccountId(String accountId);
}
