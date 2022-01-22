package com.test.controller;

import com.test.constatnt.ActionResult;
import com.test.constatnt.dto.AcountDto;
import com.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by 李传伟 on 2022/1/8 15:54
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/decrAccount")
    @Transactional
    public ActionResult<String> decrAccount(@RequestBody AcountDto acountDto){

        Boolean success = accountService.decrAccount(acountDto);

        if(success){

           return ActionResult.success("扣减用户余额成功");
        }else{

           return ActionResult.success("扣减用户余额失败");
        }
    }

}
