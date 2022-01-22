package com.test.constatnt;

import lombok.Getter;

/**
 * Create by 李传伟 on 2022/1/8 13:34
 */
public enum ConstantEnum {

    SUCCESS(100,"success"),

    FAIL(101,"fail"),

    EXCEPTION(102,"exception");


    @Getter
    private int code;

    @Getter
    private String msg;

    ConstantEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
