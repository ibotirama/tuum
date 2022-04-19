package com.tuum.tuumapi.mapppers;

import com.tuum.tuumapi.model.Balance;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

@Mapper
public interface BalanceMapper {
    @Insert("insert into account_balance(account_id, currency_code, amount) values (#{accountId}, #{currencyCode},  #{amount})")
    void create(Balance balance);

    @Select("select sum(ab.amount) from account_balance ab where ab.account_id = #{accountId} and ab.currency_code = #{currencyCode}")
    BigDecimal getBalanceBy(String accountId, String currencyCode);

    @Update("update account_balance set amount=#{balance} where account_id = #{accountId} and currency_code = #{currencyCode}")
    void updateBalance(String accountId, String currencyCode, BigDecimal balance);
}
