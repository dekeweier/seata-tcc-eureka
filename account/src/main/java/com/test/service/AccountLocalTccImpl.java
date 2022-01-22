package com.test.service;

import com.test.constatnt.util.ResultHolder;
import com.test.entity.Account;
import com.test.localTcc.AccountLocalTcc;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Create by 李传伟 on 2022/1/8 15:58
 */

@Component
@Slf4j
public class AccountLocalTccImpl implements AccountLocalTcc {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Transactional(rollbackFor = Exception.class)
    public boolean doBusiness(BusinessActionContext businessActionContext, Integer userId, BigDecimal amount) {
        log.info("第一阶段，减少账户金额并锁定账户金额 "+ "userId :"+ userId," amount:"+amount);

       //查询用户可用余额
        List<Account> accounts = jdbcTemplate.query("select balance from tcc_user where id = (?)", new BeanPropertyRowMapper<Account>(Account.class), userId);
        log.info("用户余额"+ "userId :"+ userId + " accounts:"+accounts);
        if(accounts != null){
            BigDecimal balance = accounts.get(0).getBalance();
            if(balance.compareTo(amount) == -1){
                throw new RuntimeException("账户金额不足");
            }
        }else{

            throw new RuntimeException("不存在该用户");
        }

        //冻结用户可用金额
        jdbcTemplate.update("update tcc_user set balance = balance - (?) ,frozenamount = (?) where id =(?)",new Object[]{amount, amount, userId});

        //保存一阶段try的操作标识
        ResultHolder.setResult(getClass(),businessActionContext.getXid(),"p");

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean commit(BusinessActionContext businessActionContext) {
        //第二阶段
         Integer userId = (Integer)businessActionContext.getActionContext("userId");
        BigDecimal amount = new BigDecimal(String.valueOf(businessActionContext.getActionContext("amount")));

        log.info("第二阶段扣减用户金额："+"userId: "+ userId+" amount: "+amount);

        //防止重复提交，确认try已经执行完成
        if(ResultHolder.getResult(getClass(),businessActionContext.getXid()) == null){
            return true;
        }


        //删除冻结金额，并将金额记录到客户已用金额
        jdbcTemplate.update("update tcc_user set usedamount = usedamount + (?), frozenamount = (?)",new Object[]{amount,0});

        //删除标识
        ResultHolder.removeResult(getClass(),businessActionContext.getXid());
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean rollback(BusinessActionContext businessActionContext) {
        final Integer userId = (Integer)businessActionContext.getActionContext("userId");
        BigDecimal amount = new BigDecimal(String.valueOf(businessActionContext.getActionContext("amount")));
        //第二阶段cancel
        log.info("第二阶段取消扣减用户金额：" + "userId "+ userId+" amount: "+amount);

        //防止空回滚
        if(ResultHolder.getResult(getClass(),businessActionContext.getXid()) == null){
            return true;
        }


        //将冻结金额重新加回到可用余额中，并且将已用金额进行扣除
        jdbcTemplate.update("update tcc_user set balance = balance + (?), usedamount = usedamount - (?)",new Object[]{amount, amount});

        //删除标识
        ResultHolder.removeResult(getClass(),businessActionContext.getXid());

        return true;
    }
}
