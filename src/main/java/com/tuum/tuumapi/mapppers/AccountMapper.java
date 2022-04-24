package com.tuum.tuumapi.mapppers;

import com.tuum.tuumapi.model.Account;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AccountMapper {
    @Select("select a.id, a.customer_id, a.country from ACCOUNT a where a.id = #{accountId}")
    Account findById(String accountId);

    @Insert("insert into account(id, customer_id, country) values (#{id}, #{customerId}, #{country})")
    void create(Account account);
}
