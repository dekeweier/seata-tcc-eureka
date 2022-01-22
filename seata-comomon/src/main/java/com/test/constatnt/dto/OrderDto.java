package com.test.constatnt.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Create by 李传伟 on 2022/1/8 13:56
 */
@Data
public class OrderDto implements Serializable{
    private static final long serialVersionUID = -2096315421594991978L;

    //订单id
    private Long id;

    //商品序列号
    private String code;

    //订单类型（1-实体销售；2-网络销售）
    private boolean type;

    //客户id
    private Integer customerId;

    //订单金额
    private BigDecimal amount;


    //订单状态（1未付款、2已付款、3已发货、4已签收）
    private Integer status;

    //创建时间
    private Date createTime;


}
