package com.test.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by 李传伟 on 2022/1/8 10:58
 */
@RestController("/purcharse")
public class BusinessController {

    @GetMapping
    public String test(){
        return "访问成功";
    }



}
