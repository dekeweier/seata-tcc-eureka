package com.test.service;

import com.test.LocalTcc.OrderLocalTcc;
import com.test.constatnt.ActionResult;
import com.test.constatnt.dto.AcountDto;
import com.test.constatnt.dto.OrderDto;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Create by 李传伟 on 2022/1/15 12:56
 */
@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderLocalTcc orderLocalTcc;

    @Autowired
    private RestTemplate restTemplate;


    @GlobalTransactional
    public ActionResult<String> createOrder(OrderDto order) {

        log.info("开启事务，xid为：" + RootContext.getXID());

        orderLocalTcc.doBusiness(null, order);

        AcountDto acountDto = new AcountDto();
        acountDto.setId(order.getCustomerId());
        acountDto.setBalance(order.getAmount());
        String url = "http://account/user/decrAccount";

        //修改账户余额
        ActionResult actionResult = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<Object>(acountDto, null), ActionResult.class).getBody();

        return new ActionResult<String>().setData("创建订单成功");

    }

    //该接口做测试用例，用于实践Daoclound云服务的CI/CD功能
    public Integer addTest(int a ,int b){
        return a + b;
    }
}
