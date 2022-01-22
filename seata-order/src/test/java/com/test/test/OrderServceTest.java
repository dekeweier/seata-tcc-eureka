package com.test.test;

import com.test.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Create by 李传伟 on 2022/1/8 14:48
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServceTest {

    @Autowired
    OrderService orderService;

    /**
     * 只对service服务接口写测试案例，到时自动化集成时使用
     */
    @Test
    public void test(){

        Integer sum = orderService.addTest(2, 3);
        assert 5 == sum;

    }



}
