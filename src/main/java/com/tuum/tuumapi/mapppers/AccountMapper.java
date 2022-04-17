package com.tuum.tuumapi.mapppers;

import java.util.List;

import com.tuum.tuumapi.model.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper {
    @Select("select * from account")
    List<Account> findAll();

    @Select("select * from account where account_id = #{id}")
    Account findById(String id);

    @Insert("insert into account(account_id, customer_id, country) values (#{accountId}, #{customerId}, #{country})")
    void create(Account account);
}
