package com.test.orderController;

import com.test.constatnt.ActionResult;
import com.test.constatnt.dto.OrderDto;
import com.test.constatnt.util.IdGenratorBySnowflow;
import com.test.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Create by 李传伟 on 2022/1/8 12:15
 */
@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {

    @GetMapping
    public String test(){
        return "访问成功";
    }


    @Autowired
    private OrderService orderService;

    @Autowired
    private IdGenratorBySnowflow idGenratorBySnowflow;

    //创建订单
    @PostMapping("/createOrder")
    public ActionResult<String> createOrder(@RequestBody OrderDto order){
        //使用雪花算法生成orderId
        long orderId = idGenratorBySnowflow.snowflakeId();
        order.setId(orderId);

        ActionResult<String> stringActionResult = orderService.createOrder(order);


        return stringActionResult;
    }



}
