package com.test.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Create by 李传伟 on 2022/1/15 11:12
 */
@Data
public class Account {

    //用户id
    private Integer id;

    //可用余额
    private BigDecimal balance;

    //冻结金额
    private BigDecimal frozenAmount;

    //已用金额
    private BigDecimal usedAmount;
}
