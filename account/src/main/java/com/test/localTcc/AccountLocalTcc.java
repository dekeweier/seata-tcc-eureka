package com.test.localTcc;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

import java.math.BigDecimal;

/**
 * Create by 李传伟 on 2022/1/15 10:50
 */
@LocalTCC
public interface AccountLocalTcc {

    /**
     * try 尝试
     *
     * BusinessActionContext 上下文对象，用来在两个阶段之间传递数据
     * BusinessActionContextParameter 注解的参数数据会被存入 BusinessActionContext
     * TwoPhaseBusinessAction 注解中commitMethod、rollbackMethod 属性有默认值，可以不写
     *
     * @param businessActionContext
     * @param userId
     * @param amount
     * @return
     */
    @TwoPhaseBusinessAction(name = "accountLocalTcc", commitMethod = "commit", rollbackMethod = "rollback")
    boolean doBusiness(BusinessActionContext businessActionContext,
                       @BusinessActionContextParameter(paramName = "userId") Integer userId,
                       @BusinessActionContextParameter(paramName = "amount") BigDecimal amount);

    boolean commit(BusinessActionContext businessActionContext);

    boolean rollback(BusinessActionContext businessActionContext);

}
