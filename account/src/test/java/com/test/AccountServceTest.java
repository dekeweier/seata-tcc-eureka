package com.test;

import com.test.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test(){
        List<Account> accounts = jdbcTemplate.query("select balance from tcc_user where id = ?", new BeanPropertyRowMapper<Account>(Account.class), 100);

        System.out.println(accounts);

    }

    @Test
    public void test1(){

        List<Account> accounts = jdbcTemplate.query("select used_amount from tcc_user where id = (?)", new BeanPropertyRowMapper<Account>(Account.class), 100);

        System.out.println("查询账户的结果："+accounts);
    }



}