package com.test.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.LocalTcc.OrderLocalTcc;
import com.test.constatnt.dto.OrderDto;
import com.test.constatnt.util.ResultHolder;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * Create by 李传伟 on 2022/1/8 13:30
 */

@Component
@Slf4j
public class OrderServiceTccImpl implements OrderLocalTcc {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    RestTemplate restTemplate;

    /**
     * try方法
     * BusinessActionContext：上下文对象，用于传递两阶段过程中的数据
     *
     *
     * @param businessActionContext
     * @param order
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean doBusiness(BusinessActionContext businessActionContext, OrderDto order) {
       //创建order的第一阶段：预留资源
        log.info("创建order的第一阶段：预留资源 "+ businessActionContext.getXid());

        jdbcTemplate.update("insert tcc_order(id,code,type,customer_id,amount,status,create_time) values(?,?,?,?,?,?,?)",
                new Object[] {order.getId(), order.getCode(), order.isType(),
                        order.getCustomerId(), order.getAmount(), 1, new Date()});

        //事务成功，保存一个标识供第二阶段判断
        ResultHolder.setResult(getClass(), businessActionContext.getXid(),"p");

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean commit(BusinessActionContext businessActionContext) {
        log.info("修改订单的状态 为2-已付款 "+ businessActionContext.getXid());

        //保证幂等操作，防止重复提交,如果之前未try，则禁止提交操作
        if(ResultHolder.getResult(getClass(),businessActionContext.getXid()) == null){
            return true;
        }

        //获取context中的OrderDto对象，修改其订单状态
        ObjectMapper objectMapper = new ObjectMapper();
        OrderDto order = objectMapper.convertValue(businessActionContext.getActionContext("order"),OrderDto.class);
        order.setStatus(2);

        jdbcTemplate.update("update tcc_order set status = (?) where id = (?)",
                new Object[] {order.getStatus(),order.getId()});

        //commit成功删除标志
        ResultHolder.removeResult(getClass(),businessActionContext.getXid());
        return true;
    }

    //第一阶段try没有执行完成情况下（还在执行过程中）不需要进行回滚，也就是空回滚
    //因为第一阶段有本地事务保证，事务失败时进行本地回滚
    //如果这里的第一阶段成功，但是其他的分支事务失败，则在这个cancel方法中进行回滚
    @Transactional(rollbackFor = Exception.class)
    public boolean rollback(BusinessActionContext businessActionContext) {

        //幂等性操作，判断如果是重复回滚则直接返回
        if(ResultHolder.getResult(getClass(),businessActionContext.getXid()) == null){
            return true;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        OrderDto order = objectMapper.convertValue(businessActionContext.getActionContext("order"),OrderDto.class);
        Long id = order.getId();
        //取消操作：删除创建的未付款订单
        jdbcTemplate.update("delete from tcc_order where id = (?)",new Object[]{order.getId()});

        //回滚结束，删除标志
        ResultHolder.removeResult(getClass(),businessActionContext.getXid());
        return true;
    }
}
