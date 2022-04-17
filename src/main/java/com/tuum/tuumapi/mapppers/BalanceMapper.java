package com.tuum.tuumapi.mapppers;

import com.tuum.tuumapi.model.Balance;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BalanceMapper {
    @Insert("insert into account_balance(account_id, currency_code, amount) values (#{accountId}, #{currencyCode},  #{amount})")
    void create(Balance balance);

}
