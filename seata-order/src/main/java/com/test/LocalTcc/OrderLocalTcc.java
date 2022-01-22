package com.test.LocalTcc;

import com.test.constatnt.dto.OrderDto;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * Create by 李传伟 on 2022/1/9 16:26
 */
@LocalTCC
public interface OrderLocalTcc {

    /**
     * try 尝试
     *
     * BusinessActionContext 上下文对象，用来在两个阶段之间传递数据
     * BusinessActionContextParameter 注解的参数数据会被存入 BusinessActionContext
     * TwoPhaseBusinessAction 注解中commitMethod、rollbackMethod 属性有默认值，可以不写
     *
     * @param businessActionContext
     * @param order
     * @return
     */
    @TwoPhaseBusinessAction(name = "orderLocalTcc", commitMethod = "commit", rollbackMethod = "rollback")
    boolean doBusiness(BusinessActionContext businessActionContext,
                       @BusinessActionContextParameter(paramName = "order") OrderDto order);

    boolean commit(BusinessActionContext businessActionContext);

    boolean rollback(BusinessActionContext businessActionContext);


}
