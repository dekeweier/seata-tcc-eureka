package com.test.constatnt.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Create by 李传伟 on 2022/1/8 16:34
 */
@Data
public class AcountDto {

    //用户id
    private Integer id;

    //用户余额
    private BigDecimal balance;

}
