package com.test.service;

import com.test.constatnt.dto.AcountDto;
import com.test.localTcc.AccountLocalTcc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by 李传伟 on 2022/1/15 14:38
 */
@Service
public class AccountService {

    @Autowired
    private AccountLocalTcc accountLocalTcc;

    public Boolean decrAccount(AcountDto acountDto) {

        return accountLocalTcc.doBusiness(null,acountDto.getId(),acountDto.getBalance());
    }
}
